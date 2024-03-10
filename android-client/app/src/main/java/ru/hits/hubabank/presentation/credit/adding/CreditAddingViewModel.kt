package ru.hits.hubabank.presentation.credit.adding

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.credit.adding.model.CreditAddingAction
import ru.hits.hubabank.presentation.credit.adding.model.CreditAddingState
import javax.inject.Inject

@HiltViewModel
class CreditAddingViewModel @Inject constructor(
) : BaseViewModel<CreditAddingState, CreditAddingAction>(CreditAddingState(isLoading = true)) {
}
