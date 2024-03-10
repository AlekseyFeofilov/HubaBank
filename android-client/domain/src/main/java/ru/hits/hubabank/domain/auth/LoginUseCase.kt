package ru.hits.hubabank.domain.auth

import ru.hits.hubabank.domain.auth.model.LoginModel
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
) : SimpleUseCase<LoginModel, Unit> {

    override suspend fun execute(param: LoginModel) {
        val tokens = authRemoteDataSource.login(param)
        authLocalDataSource.saveTokens(tokens)
    }
}
