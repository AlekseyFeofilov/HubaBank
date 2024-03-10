package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.Serializable

@Serializable
internal data class FullNameDto(
    val firstName: String,
    val secondName: String,
    val thirdName: String?,
)
