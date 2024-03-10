package ru.hits.hubabank.data.database.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.hits.hubabank.data.database.auth.model.TokensEntity

@Dao
internal interface TokensDao {

    @Insert
    suspend fun saveTokens(tokensEntity: TokensEntity)

    @Query("SELECT * FROM Tokens LIMIT 1")
    suspend fun getTokens(): TokensEntity?

    @Query("DELETE FROM Tokens")
    suspend fun clearTokens()
}
