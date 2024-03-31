package ru.hits.hubabank.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hits.hubabank.domain.user.ObserveProfileUseCase
import ru.hits.hubabank.presentation.core.BaseViewModel
import ru.hits.hubabank.presentation.core.ScreenAction
import ru.hits.hubabank.presentation.core.ScreenState
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
) : BaseViewModel<MainActivityState, ScreenAction>(
    MainActivityState(isDarkTheme = false)
) {

    init {
        observeProfile()
    }

    private fun observeProfile() {
        launch {
            observeProfileUseCase(Unit).collect {
                _screenState.value = _screenState.value.copy(isDarkTheme = it.getOrNull()?.isDarkTheme ?: false)
            }
        }
    }
}

data class MainActivityState(val isDarkTheme: Boolean): ScreenState
