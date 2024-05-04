package ru.hits.hubabank.presentation.credit.adding

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.FetchAllBillsUseCase
import ru.hits.hubabank.domain.bill.ObserveAllBillsUseCase
import ru.hits.hubabank.domain.credit.AddNewCreditUseCase
import ru.hits.hubabank.domain.credit.FetchCreditTermsUseCase
import ru.hits.hubabank.domain.credit.ObserveAllCreditTermsUseCase
import ru.hits.hubabank.domain.credit.model.CreateCredit
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.credit.adding.model.CreditAddingAction
import ru.hits.hubabank.presentation.credit.adding.model.CreditAddingState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CreditAddingViewModel @Inject constructor(
    private val addNewCreditUseCase: AddNewCreditUseCase,
    private val observeAllCreditTermsUseCase: ObserveAllCreditTermsUseCase,
    private val observeAllBillsUseCase: ObserveAllBillsUseCase,
    private val fetchCreditTermsUseCase: FetchCreditTermsUseCase,
    private val fetchAllBillsUseCase: FetchAllBillsUseCase,
) : BaseViewModel<CreditAddingState, CreditAddingAction>(
    CreditAddingState(
        isLoading = true,
        allCreditTerms = emptyList(),
        isCreditTermsMenuOpen = false,
        selectedTerms = null,
        allBills = emptyList(),
        isBillsMenuOpen = false,
        selectedBill = null,
        creditSum = "",
        creditDate = LocalDate.now().plusMonths(1),
        isDateDialogOpen = false,
    )
) {

    init {
        fetchCreditTerms()
        fetchBills()
        observeCreditTerms()
        observeBills()
    }

    fun navigateBack() {
        sendAction(CreditAddingAction.NavigateBack)
    }

    fun creditSumChange(newSum: String) {
        if (newSum.length > 8) {
            return
        }
        _screenState.value = _screenState.value.copy(creditSum = newSum)
    }

    fun changeTermsMenuVisible(isOpen: Boolean) {
        _screenState.value = _screenState.value.copy(isCreditTermsMenuOpen = isOpen)
    }

    fun onTermsClick(index: Int) {
        _screenState.value = _screenState.value.copy(selectedTerms = currentState.allCreditTerms[index])
        changeTermsMenuVisible(false)
    }

    fun changeBillsMenuVisible(isOpen: Boolean) {
        _screenState.value = _screenState.value.copy(isBillsMenuOpen = isOpen)
    }

    fun onBillClick(index: Int) {
        _screenState.value = _screenState.value.copy(selectedBill = currentState.allBills[index])
        changeBillsMenuVisible(false)
    }

    fun changeDateDialogVisible(isOpen: Boolean) {
        _screenState.value = _screenState.value.copy(isDateDialogOpen = isOpen)
    }

    fun newDate(dateInMillis: Long) {
        val date = Instant.ofEpochMilli(dateInMillis)
            .atZone(ZoneId.systemDefault()).toLocalDate()
        if (date < LocalDate.now().plusDays(10)) {
            sendAction(CreditAddingAction.ShowError(R.string.common_wrong_value))
            return
        }
        _screenState.value = _screenState.value.copy(creditDate = date)
        changeDateDialogVisible(false)
    }

    fun addNewCredit() {
        val selectedTerms = currentState.selectedTerms
        val selectedBill = currentState.selectedBill
        if (selectedTerms == null || selectedBill == null || currentState.creditSum.isBlank()) {
            sendAction(CreditAddingAction.ShowError(R.string.common_empty_fields_error_message))
            return
        }
        if (currentState.creditSum.toLong() < 100) {
            sendAction(CreditAddingAction.ShowError(R.string.common_wrong_value))
            return
        }
        launch {
            addNewCreditUseCase(
                CreateCredit(
                    billId = selectedBill.id,
                    principal = currentState.creditSum.toLong() * 100,
                    completionDate = currentState.creditDate,
                    creditTermsId = selectedTerms.id,
                )
            ).onSuccess {
                sendAction(CreditAddingAction.NavigateBack)
            }.onFailure {
                sendAction(CreditAddingAction.ShowError(R.string.common_error_message))
            }
        }
    }

    fun fetchCreditTerms() {
        launch {
            fetchCreditTermsUseCase(Unit).onFailure {
                sendAction(CreditAddingAction.ShowError(R.string.common_refresh_error_message))
            }
            _screenState.value = _screenState.value.copy(isLoading = false)
        }
    }

    fun fetchBills() {
        launch {
            fetchAllBillsUseCase(Unit).onFailure {
                sendAction(CreditAddingAction.ShowError(R.string.common_refresh_error_message))
            }
            _screenState.value = _screenState.value.copy(isLoading = false)
        }
    }

    private fun observeCreditTerms() {
        launch {
            observeAllCreditTermsUseCase(Unit).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(allCreditTerms = it)
                }.onFailure {
                    sendAction(CreditAddingAction.ShowError(R.string.common_error_message))
                }
            }
        }
    }

    private fun observeBills() {
        launch {
            observeAllBillsUseCase(Unit).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(allBills = it)
                }.onFailure {
                    sendAction(CreditAddingAction.ShowError(R.string.common_error_message))
                }
            }
        }
    }
}
