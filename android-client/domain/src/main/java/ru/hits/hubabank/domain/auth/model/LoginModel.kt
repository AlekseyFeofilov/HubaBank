package ru.hits.hubabank.domain.auth.model

data class LoginModel(
    val phone: String,
    val password: String,
)
