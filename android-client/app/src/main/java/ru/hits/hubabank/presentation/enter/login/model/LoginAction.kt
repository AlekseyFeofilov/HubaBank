package ru.hits.hubabank.presentation.enter.login.model

import androidx.annotation.StringRes
import ru.hits.hubabank.presentation.core.ScreenAction

sealed class LoginAction: ScreenAction {

    data object OpenRegistrationScreen : LoginAction()

    data object OpenMainScreen : LoginAction()

    data class ShowError(@StringRes val errorRes: Int) : LoginAction()
}
