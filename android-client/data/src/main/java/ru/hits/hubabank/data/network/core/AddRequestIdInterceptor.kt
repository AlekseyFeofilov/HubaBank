package ru.hits.hubabank.data.network.core

import okhttp3.Interceptor
import okhttp3.Response
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AddRequestIdInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder().apply {
            header("requestId", UUID.randomUUID().toString())
        }.build()

        return chain.proceed(request)
    }
}
