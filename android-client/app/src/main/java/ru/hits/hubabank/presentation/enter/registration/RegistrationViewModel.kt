package ru.hits.hubabank.presentation.enter.registration

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.auth.RegistrationUseCase
import ru.hits.hubabank.domain.auth.model.RegistrationModel
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.enter.registration.model.RegistrationAction
import ru.hits.hubabank.presentation.enter.registration.model.RegistrationState
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : BaseViewModel<RegistrationState, RegistrationAction>(
    RegistrationState(true, "", "", "", "", "")
) {

    fun firstNameChange(value: String) {
        _screenState.value = currentState.copy(firstName = value)
    }

    fun secondNameChange(value: String) {
        _screenState.value = currentState.copy(secondName = value)
    }

    fun thirdNameChange(value: String) {
        _screenState.value = currentState.copy(thirdName = value)
    }

    fun phoneChange(value: String) {
        _screenState.value = currentState.copy(phone = value)
    }

    fun passwordChange(value: String) {
        _screenState.value = currentState.copy(password = value)
    }

    fun openLoginScreen() {
        sendAction(RegistrationAction.OpenLoginScreen)
    }

    fun register() {
        if (currentState.firstName.isBlank() && currentState.secondName.isBlank() &&
            currentState.phone.isBlank() && currentState.password.isBlank()
        ) {
            return
        }
        launch {
            val messagingToken = Firebase.messaging.token.await()
            registrationUseCase(
                RegistrationModel(
                    firstName = currentState.firstName.trim(),
                    secondName = currentState.secondName.trim(),
                    thirdName = currentState.thirdName.trim(),
                    phone = currentState.phone.trim(),
                    password = currentState.password.trim(),
                    messagingToken = messagingToken,
                )
            ).onSuccess {
                sendAction(RegistrationAction.OpenMainScreen)
            }.onFailure {
                sendAction(RegistrationAction.ShowError(R.string.common_error_message))
            }
        }
    }
}
