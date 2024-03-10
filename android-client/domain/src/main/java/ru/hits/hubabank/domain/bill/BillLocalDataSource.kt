package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem

interface BillLocalDataSource {

    suspend fun saveBills(bills: List<Bill>)

    suspend fun saveBill(bill: Bill)

    fun observeAllBills(): Flow<List<Bill>>

    fun observeBillById(billId: String): Flow<Bill>

    suspend fun updateBill(billId: String, balanceChange: Long)

    suspend fun deleteBill(billId: String)

    suspend fun saveBillHistory(historyItems: List<BillHistoryItem>)

    fun observeBillHistory(billId: String): Flow<List<BillHistoryItem>>

    suspend fun deleteAllData()
}
