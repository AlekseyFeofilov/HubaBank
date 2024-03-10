package ru.hits.hubabank.data.database.auth

import ru.hits.hubabank.data.database.auth.model.toDomain
import ru.hits.hubabank.data.database.auth.model.toEntity
import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import ru.hits.hubabank.domain.auth.model.Tokens
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    private val tokensDao: TokensDao,
): AuthLocalDataSource {

    override suspend fun saveTokens(tokens: Tokens) {
        tokensDao.clearTokens()
        tokensDao.saveTokens(tokens.toEntity())
    }

    override suspend fun getTokens(): Tokens? {
        return tokensDao.getTokens()?.toDomain()
    }

    override suspend fun clearTokens() {
        tokensDao.clearTokens()
    }
}
