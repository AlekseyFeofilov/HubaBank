package ru.hits.hubabank.data.database.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.data.database.user.model.toDomain
import ru.hits.hubabank.data.database.user.model.toEntity
import ru.hits.hubabank.domain.user.UserLocalDataSource
import ru.hits.hubabank.domain.user.model.Profile
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserLocalDataSource {

    override suspend fun saveProfile(profile: Profile) {
        userDao.saveProfile(profile.toEntity())
    }

    override fun observeProfile(): Flow<Profile?> {
        return userDao.observeProfile().map { it?.toDomain() }
    }

    override suspend fun deleteProfile() {
        userDao.deleteProfile()
    }
}
