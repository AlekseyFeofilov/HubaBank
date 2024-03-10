package ru.hits.hubabank.presentation.enter.login.model

import ru.hits.hubabank.presentation.core.ScreenState

data class LoginState(
    val isLoading: Boolean,
    val phone: String,
    val password: String,
) : ScreenState
