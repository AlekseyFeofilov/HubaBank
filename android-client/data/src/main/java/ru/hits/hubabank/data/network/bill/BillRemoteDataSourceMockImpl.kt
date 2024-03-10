package ru.hits.hubabank.data.network.bill

import kotlinx.coroutines.flow.first
import ru.hits.hubabank.data.database.bill.BillDao
import ru.hits.hubabank.data.database.bill.model.toDomain
import ru.hits.hubabank.domain.bill.BillRemoteDataSource
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import java.time.LocalDateTime
import javax.inject.Inject

internal class BillRemoteDataSourceMockImpl @Inject constructor(
    private val billDao: BillDao,
) : BillRemoteDataSource {

    override suspend fun createNewBill(): Bill {
        return Bill(LocalDateTime.now().toString(), 80000)
    }

    override suspend fun getAllBills(): List<Bill> {
        return billDao.observeAllBills().first().map { it.toDomain() }
    }

    override suspend fun getBillById(billId: String): Bill {
        return billDao.observeBill(billId).first().toDomain()
    }

    override suspend fun updateBillBalance(billId: String, balanceChange: Long) { }

    override suspend fun closeBill(billId: String) { }

    override suspend fun getBillHistory(billId: String): List<BillHistoryItem> {
        val bills = billDao.observeAllBills().first().map { it.toDomain() }
        return listOf(
            BillHistoryItem("1", bills.first().id, 40000, BillChange.TRANSFER, LocalDateTime.now()),
            BillHistoryItem("2", bills.first().id, 90000, BillChange.REFILL, LocalDateTime.now().minusDays(1)),
            BillHistoryItem("3", bills.first().id, 1220, BillChange.CREDIT, LocalDateTime.now().minusDays(5)),
        )
    }
}
