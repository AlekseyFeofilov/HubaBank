package ru.hits.hubabank.data.network.auth

import retrofit2.http.Body
import retrofit2.http.POST
import ru.hits.hubabank.data.network.auth.model.CredentialsDto
import ru.hits.hubabank.data.network.auth.model.RegisterDto
import ru.hits.hubabank.data.network.auth.model.TokensDto

internal interface AuthApi {

    @POST("users/login")
    suspend fun login(@Body credentialsDto: CredentialsDto): TokensDto

    @POST("users/register")
    suspend fun register(@Body registerDto: RegisterDto): TokensDto
}
