package ru.hits.hubabank.data.database.credit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.credit.model.CreditEntity

@Dao
internal interface CreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredit(creditEntity: CreditEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
