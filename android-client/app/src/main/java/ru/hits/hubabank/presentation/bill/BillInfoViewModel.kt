package ru.hits.hubabank.presentation.bill

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.bill.ChangeBillBalanceUseCase
import ru.hits.hubabank.domain.bill.CloseBillUseCase
import ru.hits.hubabank.domain.bill.FetchBillHistoryUseCase
import ru.hits.hubabank.domain.bill.FetchBillUseCase
import ru.hits.hubabank.domain.bill.ObserveBillHistoryUseCase
import ru.hits.hubabank.domain.bill.ObserveBillUseCase
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.domain.bill.model.ChangeBillBalanceModel
import ru.hits.hubabank.presentation.bill.model.BillInfoAction
import ru.hits.hubabank.presentation.bill.model.BillInfoState
import ru.hits.hubabank.presentation.core.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BillInfoViewModel @Inject constructor(
    private val observeBillUseCase: ObserveBillUseCase,
    private val observeBillHistoryUseCase: ObserveBillHistoryUseCase,
    private val fetchBillUseCase: FetchBillUseCase,
    private val fetchBillHistoryUseCase: FetchBillHistoryUseCase,
    private val changeBillBalanceUseCase: ChangeBillBalanceUseCase,
    private val closeBillUseCase: CloseBillUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<BillInfoState, BillInfoAction>(
    BillInfoState(
        isLoading = true,
        bill = null,
        billHistory = emptyMap(),
        today = LocalDate.now(),
        isChangeBillDialogOpen = false,
        howChange = BillChange.REFILL,
        changeSum = "",
    )
) {

    private val billId: String = checkNotNull(savedStateHandle[BillInfoDestination.billIdArg])

    init {
        observeBill()
        observeBillHistory()

    }

    fun navigateBack() {
        sendAction(BillInfoAction.NavigateBack)
    }

    fun openDialog(howChange: BillChange) {
        _screenState.value = _screenState.value.copy(isChangeBillDialogOpen = true, howChange = howChange)
    }

    fun changeSum(value: String) {
        if (value.all { it.isDigit() } && value.length < 7) {
            _screenState.value = _screenState.value.copy(changeSum = value)
        }
    }

    fun closeDialog() {
        _screenState.value = _screenState.value.copy(isChangeBillDialogOpen = false)
    }

    fun changeBillBalance() {
        if (currentState.changeSum.isEmpty()) {
            return
        }
        launch {
            var balanceChange = currentState.changeSum.toLong()
            if (currentState.howChange == BillChange.TRANSFER) balanceChange = -balanceChange
            changeBillBalanceUseCase(
                ChangeBillBalanceModel(
                    billId = billId,
                    balanceChange = balanceChange * 100
                )
            ).onSuccess {
                _screenState.value = _screenState.value.copy(changeSum = "")
                _screenState.value = _screenState.value.copy(isChangeBillDialogOpen = false)
                fetchBillHistory()
            }
        }
    }

    fun closeBill() {
        launch {
            closeBillUseCase(billId).onSuccess {
                sendAction(BillInfoAction.NavigateBack)
            }
        }
    }

    fun fetchBill() {
        launch {
            fetchBillUseCase(billId)
        }
    }

    fun fetchBillHistory() {
        launch {
            fetchBillHistoryUseCase(billId)
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
            observeBillHistoryUseCase(billId).collect {result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(
                        billHistory = it,
                        today = LocalDate.now(),
                    )
                }
            }
        }
    }
}
