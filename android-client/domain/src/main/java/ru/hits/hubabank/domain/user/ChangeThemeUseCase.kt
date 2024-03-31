package ru.hits.hubabank.domain.user

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : SimpleUseCase<Boolean, Unit> {

    override suspend fun execute(param: Boolean) {
        userRemoteDataSource.saveTheme(isDark = param)
        userLocalDataSource.changeTheme(isDark = param)
    }
}
