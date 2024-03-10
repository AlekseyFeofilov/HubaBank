package ru.hits.hubabank.presentation.credit.info

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.credit.CloseCreditUseCase
import ru.hits.hubabank.domain.credit.FetchCreditUseCase
import ru.hits.hubabank.domain.credit.ObserveCreditUseCase
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoAction
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoState
import javax.inject.Inject

@HiltViewModel
class CreditInfoViewModel @Inject constructor(
    private val observeCreditUseCase: ObserveCreditUseCase,
    private val fetchCreditUseCase: FetchCreditUseCase,
    private val closeCreditUseCase: CloseCreditUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CreditInfoState, CreditInfoAction>(
    CreditInfoState(isLoading = true, credit = null)
) {

    private val creditId: String = checkNotNull(savedStateHandle[CreditInfoDestination.creditIdArg])

    init {
        observeCredit()
    }

    fun navigateBack() {
        sendAction(CreditInfoAction.NavigateBack)
    }

    fun closeCredit() {
        launch {
            closeCreditUseCase(creditId).onSuccess {
                sendAction(CreditInfoAction.NavigateBack)
            }
        }
    }

    fun fetchCredit() {
        launch {
            fetchCreditUseCase(creditId)
        }
    }

    private fun observeCredit() {
        launch {
            observeCreditUseCase(creditId).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(credit = it)
                }
            }
        }
    }
}
