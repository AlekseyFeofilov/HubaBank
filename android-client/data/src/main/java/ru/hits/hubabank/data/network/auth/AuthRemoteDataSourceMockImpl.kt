package ru.hits.hubabank.data.network.auth

import ru.hits.hubabank.domain.auth.AuthRemoteDataSource
import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.domain.auth.model.RegistrationModel
import ru.hits.hubabank.domain.auth.model.Tokens
import javax.inject.Inject

internal class AuthRemoteDataSourceMockImpl @Inject constructor() : AuthRemoteDataSource {

    override suspend fun login(loginParams: LoginModel): Tokens {
        return Tokens(accessToken = "123", refreshToken = "321")
    }

    override suspend fun register(registerParams: RegistrationModel): Tokens {
        return Tokens(accessToken = "123", refreshToken = "321")
    }
}
