package ru.hits.hubabank.domain.auth.model

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)
