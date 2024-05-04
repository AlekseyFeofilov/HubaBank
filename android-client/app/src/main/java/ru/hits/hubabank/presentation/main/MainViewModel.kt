package ru.hits.hubabank.presentation.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.AddNewBillUseCase
import ru.hits.hubabank.domain.bill.FetchAllBillsUseCase
import ru.hits.hubabank.domain.bill.ObserveAllBillsUseCase
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.Currency
import ru.hits.hubabank.domain.credit.FetchAllCreditsUseCase
import ru.hits.hubabank.domain.credit.ObserveAllCreditsUseCase
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.main.model.MainAction
import ru.hits.hubabank.presentation.main.model.MainState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeAllBillsUseCase: ObserveAllBillsUseCase,
    private val fetchAllBillsUseCase: FetchAllBillsUseCase,
    private val addNewBillUseCase: AddNewBillUseCase,
    private val observeAllCreditsUseCase: ObserveAllCreditsUseCase,
    private val fetchAllCreditsUseCase: FetchAllCreditsUseCase,
) : BaseViewModel<MainState, MainAction>(
    MainState(isLoading = true, isCreatingDialogOpen = false, bills = emptyList(), credits = emptyList())
) {

    init {
        observeBills()
        observeCredits()
    }

    fun openProfile() {
        sendAction(MainAction.OpenProfileScreen)
    }

    fun openBillInfo(bill: Bill) {
        sendAction(MainAction.OpenBillInfoScreen(bill.id))
    }

    fun openCreditInfo(credit: Credit) {
        sendAction(MainAction.OpenCreditInfoScreen(credit.id))
    }

    fun openBillCreatingDialog() {
        _screenState.value = _screenState.value.copy(isCreatingDialogOpen = true)
    }

    fun closeBillCreatingDialog() {
        _screenState.value = _screenState.value.copy(isCreatingDialogOpen = false)
    }

    fun addBill(currency: Currency) {
        launch {
            addNewBillUseCase(currency).onSuccess {
                _screenState.value = _screenState.value.copy(isCreatingDialogOpen = false)
            }.onFailure {
                sendAction(MainAction.ShowError(R.string.common_error_message))
            }
        }
    }

    fun addNewCredit() {
        sendAction(MainAction.OpenCreditAddingScreen)
    }

    fun fetchBills() {
        launch {
            fetchAllBillsUseCase(Unit).onFailure {
                sendAction(MainAction.ShowError(R.string.common_refresh_error_message))
            }
            _screenState.value = _screenState.value.copy(isLoading = false)
        }
    }

    fun fetchCredits() {
        launch {
            fetchAllCreditsUseCase(Unit).onFailure {
                sendAction(MainAction.ShowError(R.string.common_refresh_error_message))
            }
        }
        _screenState.value = _screenState.value.copy(isLoading = false)
    }

    private fun observeBills() {
        launch {
            observeAllBillsUseCase(Unit).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(bills = it)
                }.onFailure {
                    sendAction(MainAction.ShowError(R.string.common_error_message))
                }
            }
        }
    }

    private fun observeCredits() {
        launch {
            observeAllCreditsUseCase(Unit).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(credits = it)
                }.onFailure {
                    sendAction(MainAction.ShowError(R.string.common_error_message))
                }
            }
        }
    }
}
