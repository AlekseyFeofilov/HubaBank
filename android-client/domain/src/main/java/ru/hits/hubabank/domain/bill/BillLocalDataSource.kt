package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.bill.model.Bill

interface BillLocalDataSource {

    suspend fun saveBills(bills: List<Bill>)

    suspend fun saveBill(bill: Bill)

    fun observeAllBills(): Flow<List<Bill>>

    fun observeBillById(billId: String): Flow<Bill>

    suspend fun updateBillBalance(billId: String, balanceChange: Long)

    suspend fun changeBillHidden(billId: String, isHidden: Boolean)

    suspend fun deleteBill(billId: String)

    suspend fun deleteAllData()
}
