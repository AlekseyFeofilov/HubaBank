package ru.hits.hubabank.domain.auth.model

data class LoginModel(
    val tokenSSO: String,
    val messagingToken: String,
)
