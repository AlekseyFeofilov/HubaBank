package ru.hits.hubabank.presentation.credit.rate

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.credit.rate.model.CreditRatesAction
import ru.hits.hubabank.presentation.credit.rate.model.CreditRatesState
import javax.inject.Inject

@HiltViewModel
class CreditRatesViewModel @Inject constructor(
) : BaseViewModel<CreditRatesState, CreditRatesAction>(CreditRatesState(isLoading = true)) {
}
