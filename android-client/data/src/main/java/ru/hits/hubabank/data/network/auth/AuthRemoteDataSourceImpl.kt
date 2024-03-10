package ru.hits.hubabank.data.network.auth

import ru.hits.hubabank.data.network.auth.model.toDomain
import ru.hits.hubabank.data.network.auth.model.toDto
import ru.hits.hubabank.domain.auth.AuthRemoteDataSource
import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.domain.auth.model.RegistrationModel
import ru.hits.hubabank.domain.auth.model.Tokens
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
) : AuthRemoteDataSource {

    override suspend fun login(loginParams: LoginModel): Tokens {
        return authApi.login(loginParams.toDto()).toDomain()
    }

    override suspend fun register(registerParams: RegistrationModel): Tokens {
        return authApi.register(registerParams.toDto()).toDomain()
    }
}
