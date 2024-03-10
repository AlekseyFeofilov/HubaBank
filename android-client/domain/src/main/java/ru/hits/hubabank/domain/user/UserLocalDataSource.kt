package ru.hits.hubabank.domain.user

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.user.model.Profile

interface UserLocalDataSource {

    suspend fun saveProfile(profile: Profile)

    fun observeProfile(): Flow<Profile?>

    suspend fun deleteProfile()
}
