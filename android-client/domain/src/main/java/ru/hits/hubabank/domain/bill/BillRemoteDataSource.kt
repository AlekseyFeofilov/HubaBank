package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.domain.bill.model.Currency

interface BillRemoteDataSource {

    suspend fun createNewBill(currency: Currency): Bill

    suspend fun getAllBills(): List<Bill>

    suspend fun getBillById(billId: String): Bill

    suspend fun saveHiddenMode(billId: String, isHidden: Boolean)

    suspend fun giveMoneyForBill(billId: String, balanceChange: Long)

    suspend fun withdrawMoneyFromBill(billId: String, balanceChange: Long)

    suspend fun transferMoneyToBill(sourceBillId: String, balanceChange: Long, targetBillId: String)

    suspend fun closeBill(billId: String)

    fun startObserveBillHistory(billId: String): Flow<BillHistoryItem>

    fun endObserveBillHistory()
}
