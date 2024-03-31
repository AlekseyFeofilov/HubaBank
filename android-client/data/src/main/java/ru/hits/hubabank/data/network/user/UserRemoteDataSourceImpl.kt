package ru.hits.hubabank.data.network.user

import ru.hits.hubabank.data.network.user.model.toDomain
import ru.hits.hubabank.domain.user.UserRemoteDataSource
import ru.hits.hubabank.domain.user.model.Profile
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRemoteDataSource {

    override suspend fun getProfile(): Profile {
        return userApi.getProfile().toDomain()
    }

    override suspend fun saveTheme(isDark: Boolean) {
        userApi.saveTheme(isDark)
    }
}
