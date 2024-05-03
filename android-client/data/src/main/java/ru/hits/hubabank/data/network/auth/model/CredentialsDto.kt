package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.auth.model.LoginModel

@Serializable
internal data class CredentialsDto(
    @SerialName("jwtSOO") val tokenSSO: String,
    val messagingToken: String,
)

internal fun LoginModel.toDto(): CredentialsDto {
    return CredentialsDto(
        tokenSSO = tokenSSO,
        messagingToken = messagingToken,
    )
}
