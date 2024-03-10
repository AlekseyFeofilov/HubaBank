package ru.hits.hubabank.presentation.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.user.ExitUseCase
import ru.hits.hubabank.domain.user.FetchProfileUseCase
import ru.hits.hubabank.domain.user.ObserveProfileUseCase
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.profile.model.ProfileAction
import ru.hits.hubabank.presentation.profile.model.ProfileState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val exitUseCase: ExitUseCase,
) : BaseViewModel<ProfileState, ProfileAction>(
    ProfileState(isLoading = true, fullName = "", phone = "")
) {

    init {
        observeProfile()
    }

    fun navigateBack() {
        sendAction(ProfileAction.NavigateBack)
    }

    fun exit() {
        launch {
            exitUseCase(Unit).onSuccess {
                sendAction(ProfileAction.Exit)
            }.onFailure {
                sendAction(ProfileAction.Exit)
            }
        }
    }

    fun fetchProfile() {
        launch {
            fetchProfileUseCase(Unit)
        }
    }

    private fun observeProfile() {
        launch {
            observeProfileUseCase(Unit).collect { result ->
                result.onSuccess {
                    _screenState.value = _screenState.value.copy(
                        fullName = it?.name ?: "",
                        phone = it?.phone ?: "",
                    )
                }
            }
        }
    }
}
