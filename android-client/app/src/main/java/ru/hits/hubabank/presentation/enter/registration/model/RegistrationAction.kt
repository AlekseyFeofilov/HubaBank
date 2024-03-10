package ru.hits.hubabank.presentation.enter.registration.model

import ru.hits.hubabank.presentation.core.ScreenAction

sealed class RegistrationAction: ScreenAction {

    data object OpenLoginScreen : RegistrationAction()

    data object OpenMainScreen : RegistrationAction()
}
