package ru.hits.hubabank.domain.auth

import ru.hits.hubabank.domain.auth.model.Tokens

interface AuthLocalDataSource {

    suspend fun saveTokens(tokens: Tokens)

    suspend fun getTokens(): Tokens?

    suspend fun clearTokens()
}
