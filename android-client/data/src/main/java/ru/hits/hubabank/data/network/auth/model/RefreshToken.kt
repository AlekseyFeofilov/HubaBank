package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshTokenDto(
    val refresh: String,
)
