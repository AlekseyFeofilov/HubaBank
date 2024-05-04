package ru.hits.hubabank.presentation.credit.info.model

import androidx.annotation.StringRes
import ru.hits.hubabank.presentation.core.ScreenAction

sealed class CreditInfoAction: ScreenAction {

    data object NavigateBack : CreditInfoAction()

    data class ShowError(@StringRes val errorRes: Int) : CreditInfoAction()
}
