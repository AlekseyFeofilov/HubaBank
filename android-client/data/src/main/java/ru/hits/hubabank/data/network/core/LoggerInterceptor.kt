package ru.hits.hubabank.data.network.core

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class LoggerInterceptor @Inject constructor() : Interceptor {

    private val okHttpClient = OkHttpClient.Builder().apply {
        val logLevel = HttpLoggingInterceptor.Level.BODY
        addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
    }.build()

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        try {
            val buffer = Buffer()
            response.request.body?.writeTo(buffer)
            val contentType = response.request.body?.contentType()
            val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            val requestBody = try {
                buffer.readString(charset)
            } catch (_: Exception) {
                ""
            }

            val responseBodyCopy = response.peekBody(Long.MAX_VALUE)

            val logDto = LogDto(
                requestId = response.request.headers["requestId"],
                request = LogRequestDto(
                    url = response.request.url.toString(),
                    method = response.request.method,
                    headers = response.request.headers.toMap(),
                    body = requestBody,
                ),
                response = LogResponseDto(
                    status = response.code,
                    headers = response.headers.toMap(),
                    body = responseBodyCopy.string(),
                ),
            )
            val logRequest = Request.Builder()
                .url(LOGGER_SERVICE_URL)
                .post(
                    Json.encodeToString(logDto).toRequestBody("application/json".toMediaType())
                )
                .build()

            okHttpClient.newCall(logRequest).execute()
        } catch (e: Exception) {
            println("!!! Ошибка при отправке логов: ${e.message}")
        }
        return response
    }

    private companion object {
        const val LOGGER_SERVICE_URL = "http://194.147.90.192:9006/log/api/v1"
    }
}
