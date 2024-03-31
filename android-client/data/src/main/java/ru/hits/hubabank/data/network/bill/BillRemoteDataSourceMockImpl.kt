package ru.hits.hubabank.data.network.bill

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import ru.hits.hubabank.data.database.bill.BillDao
import ru.hits.hubabank.data.database.bill.model.toDomain
import ru.hits.hubabank.domain.bill.BillRemoteDataSource
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import java.time.LocalDateTime
import javax.inject.Inject

internal class BillRemoteDataSourceMockImpl @Inject constructor(
    private val billDao: BillDao,
) : BillRemoteDataSource {

    override suspend fun createNewBill(): Bill {
        return Bill(LocalDateTime.now().toString(), 80000, isHidden = false)
    }

    override suspend fun getAllBills(): List<Bill> {
        return billDao.observeAllBills().first().map { it.toDomain() }
    }

    override suspend fun getBillById(billId: String): Bill {
        return billDao.observeBill(billId).first().toDomain()
    }

    override suspend fun saveHiddenMode(billId: String, isHidden: Boolean) {
        return billDao.changeBillHidden(billId, isHidden)
    }

    override suspend fun updateBillBalance(billId: String, balanceChange: Long) { }

    override suspend fun closeBill(billId: String) { }

    override fun startObserveBillHistory(billId: String): Flow<BillHistoryItem> {
        TODO("Not yet implemented")
    }

    override fun endObserveBillHistory() {
        TODO("Not yet implemented")
    }
}
