package ru.hits.hubabank.data.network.core

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.hits.hubabank.data.network.auth.AuthRefreshRequest
import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRefreshRequest: AuthRefreshRequest,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = getFreshAccessToken(response.request)
        return if (accessToken != null) {
            response.request.newBuilder()
                .header(NetworkConstant.AUTHORIZATION_HEADER, accessToken)
                .build()
        } else {
            null
        }
    }

    @Synchronized
    private fun getFreshAccessToken(request: Request): String? = runBlocking {
        val tokens = authLocalDataSource.getTokens() ?: return@runBlocking null
        if ("Bearer ${tokens.accessToken}" != request.header(NetworkConstant.AUTHORIZATION_HEADER)) {
            return@runBlocking tokens.accessToken
        }

        val tokenResponse = authRefreshRequest.refresh(tokens.refreshToken)
        if (tokenResponse != null) {
            authLocalDataSource.saveTokens(tokenResponse)
        }
        return@runBlocking tokenResponse?.accessToken
    }
}
