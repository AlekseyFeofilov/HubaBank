package ru.hits.hubabank.presentation.bill.model

import ru.hits.hubabank.presentation.core.ScreenAction

sealed class BillInfoAction: ScreenAction {

    data object NavigateBack : BillInfoAction()
}
