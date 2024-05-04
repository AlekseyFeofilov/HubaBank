package ru.hits.hubabank.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.hits.hubabank.data.network.auth.AuthApi
import ru.hits.hubabank.data.network.bill.BillApi
import ru.hits.hubabank.data.network.core.AddRequestIdInterceptor
import ru.hits.hubabank.data.network.core.AuthInterceptor
import ru.hits.hubabank.data.network.core.LoggerInterceptor
import ru.hits.hubabank.data.network.core.NetworkConstant
import ru.hits.hubabank.data.network.core.TokenAuthenticator
import ru.hits.hubabank.data.network.credit.CreditApi
import ru.hits.hubabank.data.network.user.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @AuthOkHttpClient
    @Singleton
    @Provides
    fun provideAuthHttpClient(
        addRequestIdInterceptor: AddRequestIdInterceptor,
        loggerInterceptor: LoggerInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(addRequestIdInterceptor)
            addInterceptor(loggerInterceptor)
        }.build()
    }

    @CommonOkHttpClient
    @Singleton
    @Provides
    fun provideCommonHttpClient(
        authInterceptor: AuthInterceptor,
        authenticator: TokenAuthenticator,
        addRequestIdInterceptor: AddRequestIdInterceptor,
        loggerInterceptor: LoggerInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(authInterceptor)
            addInterceptor(addRequestIdInterceptor)
            addInterceptor(loggerInterceptor)
            authenticator(authenticator)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@CommonOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBillApi(retrofit: Retrofit): BillApi {
        return retrofit.create(BillApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCreditApi(retrofit: Retrofit): CreditApi {
        return retrofit.create(CreditApi::class.java)
    }
}
