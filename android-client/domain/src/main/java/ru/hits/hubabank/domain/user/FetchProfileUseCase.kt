package ru.hits.hubabank.domain.user

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : SimpleUseCase<Unit, Unit> {
    override suspend fun execute(param: Unit) {
        val profile = userRemoteDataSource.getProfile()
        userLocalDataSource.saveProfile(profile)
    }
}
