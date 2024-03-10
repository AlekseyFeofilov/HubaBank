package ru.hits.hubabank.presentation.enter.login.model

import ru.hits.hubabank.presentation.core.ScreenAction

sealed class LoginAction: ScreenAction {

    data object OpenRegistrationScreen : LoginAction()

    data object OpenMainScreen : LoginAction()
}
