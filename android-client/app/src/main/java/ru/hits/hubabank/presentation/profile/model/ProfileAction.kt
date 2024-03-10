package ru.hits.hubabank.presentation.profile.model

import ru.hits.hubabank.presentation.core.ScreenAction

sealed class ProfileAction: ScreenAction {

    data object NavigateBack : ProfileAction()

    data object Exit : ProfileAction()
}
