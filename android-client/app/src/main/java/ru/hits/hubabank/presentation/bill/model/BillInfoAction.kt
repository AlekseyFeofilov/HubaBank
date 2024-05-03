package ru.hits.hubabank.presentation.bill.model

import androidx.annotation.StringRes
import ru.hits.hubabank.presentation.core.ScreenAction

sealed class BillInfoAction: ScreenAction {

    data object NavigateBack : BillInfoAction()

    data class ShowError(@StringRes val errorRes: Int) : BillInfoAction()
}
