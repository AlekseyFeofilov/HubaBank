package ru.hits.hubabank.presentation.enter.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.auth.LoginUseCase
import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.domain.user.FetchProfileUseCase
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.enter.login.model.LoginAction
import ru.hits.hubabank.presentation.enter.login.model.LoginState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LoginState, LoginAction>(LoginState(true, "", "")) {

    private val tokenSSO: String? = savedStateHandle[LoginDestination.tokenSSO]

    init {
        if (tokenSSO != null) {
            login(tokenSSO)
        } else {
            isAlreadyInSystem()
        }
    }

    fun phoneChange(value: String) {
        _screenState.value = currentState.copy(phone = value)
    }

    fun passwordChange(value: String) {
        _screenState.value = currentState.copy(password = value)
    }

    fun openRegistrationScreen() {
        sendAction(LoginAction.OpenRegistrationScreen)
    }

    fun openAuthPage(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AUTH_PAGE))
        context.startActivity(intent)
    }

    private fun login(token: String) {
        launch {
            loginUseCase(
                LoginModel(
                    tokenSSO = token,
                )
            ).onSuccess {
                fetchProfileUseCase(Unit)
                sendAction(LoginAction.OpenMainScreen)
            }
        }
    }

    private fun isAlreadyInSystem() {
        launch {
            fetchProfileUseCase(Unit).onSuccess {
                sendAction(LoginAction.OpenMainScreen)
            }
        }
    }

    private companion object {
        const val AUTH_PAGE = "http://194.147.90.192:9003/users/api/v1/auth_page?redirectedUrl=huba://enter"
    }
}