package ru.hits.hubabank.presentation.main.model

import ru.hits.hubabank.presentation.core.ScreenAction

sealed class MainAction: ScreenAction {

    data object OpenProfileScreen : MainAction()

    data class OpenBillInfoScreen(val billId: String) : MainAction()

    data class OpenCreditInfoScreen(val creditId: String) : MainAction()

    data object OpenOpenCreditAddingScreen : MainAction()
}
