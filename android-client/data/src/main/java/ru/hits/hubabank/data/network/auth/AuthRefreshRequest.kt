package ru.hits.hubabank.data.network.auth

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hits.hubabank.data.di.AuthOkHttpClient
import ru.hits.hubabank.data.network.auth.model.RefreshTokenDto
import ru.hits.hubabank.data.network.auth.model.TokensDto
import ru.hits.hubabank.data.network.auth.model.toDomain
import ru.hits.hubabank.data.network.core.NetworkConstant
import ru.hits.hubabank.domain.auth.model.Tokens
import javax.inject.Inject

internal class AuthRefreshRequest @Inject constructor(
    @AuthOkHttpClient private val okHttpClient: OkHttpClient,
) {

    fun refresh(refreshToken: String): Tokens? {
        val bodyString = Json.encodeToString(RefreshTokenDto(refreshToken))

        val request = Request.Builder()
            .url("${NetworkConstant.BASE_URL}$REFRESH_QUERY_PATH")
            .post(bodyString.toRequestBody("application/json".toMediaType()))
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        if (!response.isSuccessful || responseBody == null) return null

        val tokens: TokensDto = Json.decodeFromString(responseBody)
        return tokens.toDomain()
    }

    private companion object {
        const val REFRESH_QUERY_PATH = "users/refresh"
    }
}
