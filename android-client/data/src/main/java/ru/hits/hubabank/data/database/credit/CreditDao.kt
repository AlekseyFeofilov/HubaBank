package ru.hits.hubabank.data.database.credit

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.credit.model.CreditEntity

@Dao
internal interface CreditDao {

    @Upsert
    suspend fun insertCredit(creditEntity: CreditEntity)

    @Upsert
    suspend fun insertCredits(bills: List<CreditEntity>)

    @Query("SELECT * FROM Credit")
    fun observeAllCredits(): Flow<List<CreditEntity>>

    @Query("SELECT * FROM Credit WHERE id = :creditId")
    fun observeCredit(creditId: String): Flow<CreditEntity>

    @Query("DELETE FROM Credit WHERE id IN (:creditIds)")
    suspend fun deleteCredits(creditIds: List<String>)

    @Query("DELETE FROM Credit WHERE id = :creditId")
    suspend fun deleteCredit(creditId: String)

    @Query("DELETE FROM Credit")
    suspend fun deleteAllCredits()
}
