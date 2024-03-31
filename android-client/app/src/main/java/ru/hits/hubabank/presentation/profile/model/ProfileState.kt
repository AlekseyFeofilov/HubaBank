package ru.hits.hubabank.presentation.profile.model

import ru.hits.hubabank.presentation.core.ScreenState

data class ProfileState(
    val isLoading: Boolean,
    val fullName: String,
    val phone: String,
    val isDarkTheme: Boolean,
) : ScreenState
