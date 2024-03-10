package ru.hits.hubabank.domain.auth

import ru.hits.hubabank.domain.auth.model.RegistrationModel
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
) : SimpleUseCase<RegistrationModel, Unit> {

    override suspend fun execute(param: RegistrationModel) {
        val tokens = authRemoteDataSource.register(param)
        authLocalDataSource.saveTokens(tokens)
    }
}
