package ru.hits.hubabank.presentation.enter.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.auth.LoginUseCase
import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.enter.login.model.LoginAction
import ru.hits.hubabank.presentation.enter.login.model.LoginState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginAction>(LoginState(true, "", "")) {

    fun phoneChange(value: String) {
        _screenState.value = currentState.copy(phone = value)
    }

    fun passwordChange(value: String) {
        _screenState.value = currentState.copy(password = value)
    }

    fun openRegistrationScreen() {
        sendAction(LoginAction.OpenRegistrationScreen)
    }

    fun login() {
        if (currentState.phone.isBlank() && currentState.password.isBlank()) return
        launch {
            loginUseCase(
                LoginModel(
                    phone = currentState.phone.trim(),
                    password = currentState.password.trim(),
                )
            ).onSuccess {
                sendAction(LoginAction.OpenMainScreen)
            }
        }
    }
}