package ru.hits.hubabank.presentation.bill

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.bill.ChangeBillBalanceUseCase
import ru.hits.hubabank.domain.bill.ChangeBillHiddenUseCase
import ru.hits.hubabank.domain.bill.CloseBillUseCase
import ru.hits.hubabank.domain.bill.EndObserveBillHistoryUseCase
import ru.hits.hubabank.domain.bill.FetchBillUseCase
import ru.hits.hubabank.domain.bill.ObserveBillUseCase
import ru.hits.hubabank.domain.bill.StartObserveBillHistoryUseCase
import ru.hits.hubabank.domain.bill.model.BillChangeReason
import ru.hits.hubabank.domain.bill.model.ChangeBillBalanceModel
import ru.hits.hubabank.domain.bill.model.ChangeBillHiddenModel
import ru.hits.hubabank.presentation.bill.model.BillInfoAction
import ru.hits.hubabank.presentation.bill.model.BillInfoState
import ru.hits.hubabank.presentation.core.BaseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class BillInfoViewModel @Inject constructor(
    private val observeBillUseCase: ObserveBillUseCase,
    private val startObserveBillHistoryUseCase: StartObserveBillHistoryUseCase,
    private val endObserveBillHistoryUseCase: EndObserveBillHistoryUseCase,
    private val fetchBillUseCase: FetchBillUseCase,
    private val changeBillBalanceUseCase: ChangeBillBalanceUseCase,
    private val changeBillHiddenUseCase: ChangeBillHiddenUseCase,
    private val closeBillUseCase: CloseBillUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<BillInfoState, BillInfoAction>(
    BillInfoState(
        isLoading = true,
        bill = null,
        billHistory = emptyMap(),
        today = LocalDate.now(),
        isChangeBillDialogOpen = false,
        howChange = BillChangeReason.TERMINAL,
        targetBill = "",
        changeSum = "",
    )
) {

    private val billId: String = checkNotNull(savedStateHandle[BillInfoDestination.billIdArg])

    init {
        observeBill()
        observeBillHistory()
    }

    fun navigateBack() {
        endObserveBillHistory()
    }

    fun openDialog(howChange: BillChangeReason) {
        _screenState.value = _screenState.value.copy(isChangeBillDialogOpen = true, howChange = howChange)
    }

    fun changeSum(value: String) {
        if (
            value.all { it.isDigit() || it == '.' } && value.count { it == '.' } <= 1 &&
            value.substringAfter('.', "").length <=2 && value.length < 9
            ) {
            _screenState.value = _screenState.value.copy(changeSum = value)
        }
    }

    fun changeTargetBill(value: String) {
        if (value.length < 50) {
            _screenState.value = _screenState.value.copy(targetBill = value)
        }
    }

    fun closeDialog() {
        _screenState.value = _screenState.value.copy(isChangeBillDialogOpen = false)
    }

    fun changeBillBalance() {
        val reasonChange = currentState.howChange
        if (currentState.changeSum.isEmpty() ||
            (reasonChange == BillChangeReason.USER && (currentState.targetBill.isEmpty() || currentState.bill?.id == currentState.targetBill))
        ) {
            return
        }
        launch {
            val balanceChange = (currentState.changeSum.toDouble() * 100).toLong()
            changeBillBalanceUseCase(
                ChangeBillBalanceModel(
                    changeReason = reasonChange,
                    billId = billId,
                    balanceChange = balanceChange,
                    targetBill = currentState.targetBill.trim(),
                )
            ).onSuccess {
                _screenState.value = _screenState.value.copy(
                    changeSum = "",
                    targetBill = "",
                    isChangeBillDialogOpen = false,
                )
            }
        }
    }

    fun changeBillHidden(isHidden: Boolean) {
        launch {
            changeBillHiddenUseCase(
                ChangeBillHiddenModel(billId, isHidden)
            )
        }
    }

    fun closeBill() {
        launch {
            closeBillUseCase(billId).onSuccess {
                endObserveBillHistory()
            }
        }
    }

    fun fetchBill() {
        launch {
            fetchBillUseCase(billId)
        }
    }

    private fun observeBill() {
        launch {
            observeBillUseCase(billId).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(bill = it)
                }
            }
        }
    }

    private fun observeBillHistory() {
        launch {
            startObserveBillHistoryUseCase(billId).collect { historyItem ->
                val billHistory = currentState.billHistory.toMutableMap()
                val sameDateItems = billHistory[historyItem.dateTime.toLocalDate()]
                val newList = if (sameDateItems == null) {
                    listOf(historyItem)
                } else {
                    listOf(historyItem).plus(sameDateItems)
                }
                billHistory[historyItem.dateTime.toLocalDate()] = newList
                _screenState.value = _screenState.value.copy(
                    billHistory = billHistory.toSortedMap(compareByDescending { it.toString() }),
                    today = LocalDate.now(),
                )

                if (LocalDateTime.now().minusMinutes(1) < historyItem.dateTime) {
                    fetchBill()
                }
            }
        }
    }

    private fun endObserveBillHistory() {
        launch {
            kotlin.runCatching {
                endObserveBillHistoryUseCase()
            }.onSuccess {
                sendAction(BillInfoAction.NavigateBack)
            }.onFailure {
                sendAction(BillInfoAction.NavigateBack)
            }
        }
    }
}