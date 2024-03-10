package ru.hits.hubabank.presentation.credit.info

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoAction
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoState
import javax.inject.Inject

@HiltViewModel
class CreditInfoViewModel @Inject constructor(
) : BaseViewModel<CreditInfoState, CreditInfoAction>(CreditInfoState(isLoading = true)) {
}
