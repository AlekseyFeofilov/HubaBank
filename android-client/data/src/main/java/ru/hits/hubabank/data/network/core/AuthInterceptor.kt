package ru.hits.hubabank.data.network.core

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            authLocalDataSource.getTokens()?.accessToken
        }

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader(NetworkConstant.AUTHORIZATION_HEADER, "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}
