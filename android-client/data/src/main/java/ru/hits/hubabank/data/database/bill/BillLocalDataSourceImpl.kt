package ru.hits.hubabank.data.database.bill

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.data.database.bill.model.toDomain
import ru.hits.hubabank.data.database.bill.model.toEntity
import ru.hits.hubabank.domain.bill.BillLocalDataSource
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import javax.inject.Inject

internal class BillLocalDataSourceImpl @Inject constructor(
    private val billDao: BillDao,
    private val billHistoryDao: BillHistoryDao,
) : BillLocalDataSource {

    override suspend fun saveBills(bills: List<Bill>) {
        val oldBills = billDao.observeAllBills().first()
        val billsForDelete = oldBills.filter { old -> bills.none { it.id == old.id} }
        billDao.insertBills(bills.map { it.toEntity() })
        billDao.deleteBills(billsForDelete.map { it.id })
    }

    override suspend fun saveBill(bill: Bill) {
        billDao.insertBill(bill.toEntity())
    }

    override fun observeAllBills(): Flow<List<Bill>> {
        return billDao.observeAllBills().map { list -> list.map { it.toDomain() } }
    }

    override fun observeBillById(billId: String): Flow<Bill> {
        return billDao.observeBill(billId).map { it.toDomain() }
    }

    override suspend fun updateBill(billId: String, balanceChange: Long) {
        billDao.updateBill(billId, balanceChange)
    }

    override suspend fun deleteBill(billId: String) {
        billDao.deleteBill(billId)
    }

    override suspend fun saveBillHistory(historyItems: List<BillHistoryItem>) {
        billHistoryDao.saveBillHistory(historyItems.map { it.toEntity() })
    }

    override fun observeBillHistory(billId: String): Flow<List<BillHistoryItem>> {
        return billHistoryDao.observeBillHistory(billId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteAllData() {
        billDao.deleteAllBills()
        billHistoryDao.deleteAllHistory()
    }
}
