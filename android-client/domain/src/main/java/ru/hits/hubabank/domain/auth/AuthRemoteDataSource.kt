package ru.hits.hubabank.domain.auth

import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.domain.auth.model.RegistrationModel
import ru.hits.hubabank.domain.auth.model.Tokens

interface AuthRemoteDataSource {

    suspend fun login(loginParams: LoginModel): Tokens

    suspend fun register(registerParams: RegistrationModel): Tokens
}
