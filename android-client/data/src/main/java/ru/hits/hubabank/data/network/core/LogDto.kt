package ru.hits.hubabank.data.network.core

import kotlinx.serialization.Serializable

@Serializable
internal data class LogDto(
    val requestId: String?,
    val publishService: String = "android-client",
    val request: LogRequestDto,
    val response: LogResponseDto,
)

@Serializable
internal data class LogRequestDto(
    val url: String,
    val method: String,
    val headers: Map<String, String>,
    val body: String,
)

@Serializable
internal data class LogResponseDto(
    val status: Int,
    val headers: Map<String, String>,
    val body: String,
)
