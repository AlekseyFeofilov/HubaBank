package ru.hits.hubabank.presentation.profile.model

import androidx.annotation.StringRes
import ru.hits.hubabank.presentation.core.ScreenAction

sealed class ProfileAction: ScreenAction {

    data object NavigateBack : ProfileAction()

    data object Exit : ProfileAction()

    data class ShowError(@StringRes val errorRes: Int) : ProfileAction()
}
