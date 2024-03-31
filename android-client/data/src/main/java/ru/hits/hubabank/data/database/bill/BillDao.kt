package ru.hits.hubabank.data.database.bill

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.bill.model.BillEntity

@Dao
internal interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(billEntity: BillEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBills(bills: List<BillEntity>)

    @Query("SELECT * FROM Bill")
    fun observeAllBills(): Flow<List<BillEntity>>

    @Query("SELECT * FROM Bill WHERE id = :billId")
    fun observeBill(billId: String): Flow<BillEntity>

    @Query("UPDATE Bill SET balance = balance + :changeBalance WHERE id = :billId")
    suspend fun updateBillBalance(billId: String, changeBalance: Long)

    @Query("UPDATE Bill SET isHidden = :isHidden WHERE id = :billId")
    suspend fun changeBillHidden(billId: String, isHidden: Boolean)

    @Query("DELETE FROM Bill WHERE id IN (:billIds)")
    suspend fun deleteBills(billIds: List<String>)

    @Query("DELETE FROM Bill WHERE id = :billId")
    suspend fun deleteBill(billId: String)

    @Query("DELETE FROM Bill")
    suspend fun deleteAllBills()
}
