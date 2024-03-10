package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem

interface BillRemoteDataSource {

    suspend fun createNewBill(): Bill

    suspend fun getAllBills(): List<Bill>

    suspend fun getBillById(billId: String): Bill

    suspend fun updateBillBalance(billId: String, balanceChange: Long)

    suspend fun closeBill(billId: String)

    suspend fun getBillHistory(billId: String): List<BillHistoryItem>
}
