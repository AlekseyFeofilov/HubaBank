package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.auth.model.LoginModel

@Serializable
internal data class CredentialsDto(
    val phoneNumber: String,
    val password: String,
)

internal fun LoginModel.toDto(): CredentialsDto {
    return CredentialsDto(
        phoneNumber = phone,
        password = password,
    )
}
