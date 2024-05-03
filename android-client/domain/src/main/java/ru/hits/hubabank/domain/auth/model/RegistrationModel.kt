package ru.hits.hubabank.domain.auth.model

data class RegistrationModel(
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val phone: String,
    val password: String,
    val messagingToken: String,
)
