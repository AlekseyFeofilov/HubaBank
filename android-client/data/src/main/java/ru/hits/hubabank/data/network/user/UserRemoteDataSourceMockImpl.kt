package ru.hits.hubabank.data.network.user

import ru.hits.hubabank.domain.user.UserRemoteDataSource
import ru.hits.hubabank.domain.user.model.Profile
import javax.inject.Inject

internal class UserRemoteDataSourceMockImpl @Inject constructor() : UserRemoteDataSource {

    override suspend fun getProfile(): Profile {
        return Profile(userId = "123", name = "Иванов Иван Иванович", "+78005553535", false)
    }

    override suspend fun saveTheme(isDark: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}
