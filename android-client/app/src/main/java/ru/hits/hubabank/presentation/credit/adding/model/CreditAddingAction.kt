package ru.hits.hubabank.presentation.credit.adding.model

import androidx.annotation.StringRes
import ru.hits.hubabank.presentation.core.ScreenAction

sealed class CreditAddingAction: ScreenAction {

    data object NavigateBack : CreditAddingAction()

    data class OpenCreditInfoScreen(val creditId: String) : CreditAddingAction()

    data class ShowError(@StringRes val errorRes: Int) : CreditAddingAction()
}
