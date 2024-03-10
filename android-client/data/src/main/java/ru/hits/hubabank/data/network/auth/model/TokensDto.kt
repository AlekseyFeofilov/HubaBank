package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.auth.model.Tokens

@Serializable
internal data class TokensDto(
    val accessToken: String,
    val refreshToken: String,
)

internal fun TokensDto.toDomain(): Tokens {
    return Tokens(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}
