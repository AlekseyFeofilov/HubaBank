package ru.hits.hubabank.data.database.bill

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.bill.model.BillHistoryItemEntity

@Dao
internal interface BillHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBillHistory(billHistoryItems: List<BillHistoryItemEntity>)

    @Query("SELECT * FROM Billhistoryitem WHERE billId = :billId ORDER BY dateTime DESC")
    fun observeBillHistory(billId: String): Flow<List<BillHistoryItemEntity>>

    @Query("DELETE FROM Billhistoryitem")
    suspend fun deleteAllHistory()
}
