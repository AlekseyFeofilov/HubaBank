package ru.hits.hubabank.presentation.enter.registration.model

import ru.hits.hubabank.presentation.core.ScreenState

data class RegistrationState(
    val isLoading: Boolean,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val phone: String,
    val password: String,
) : ScreenState
