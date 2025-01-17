package ru.hits.hubabank.domain.user

import ru.hits.hubabank.domain.user.model.Profile

interface UserRemoteDataSource {

    suspend fun getProfile(): Profile

    suspend fun saveTheme(isDark: Boolean)

    suspend fun logout()
}
