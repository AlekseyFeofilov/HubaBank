package ru.hits.hubabank.data.network.bill

import ru.hits.hubabank.data.network.bill.model.TransactionCreationDto
import ru.hits.hubabank.data.network.bill.model.toDomain
import ru.hits.hubabank.domain.bill.BillRemoteDataSource
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import javax.inject.Inject

internal class BillRemoteDataSourceImpl @Inject constructor(
    private val billApi: BillApi,
) : BillRemoteDataSource {

    override suspend fun createNewBill(): Bill {
        return billApi.createNewBill().toDomain()
    }

    override suspend fun getAllBills(): List<Bill> {
        return billApi.getAllBills().map { it.toDomain() }
    }

    override suspend fun getBillById(billId: String): Bill {
        return billApi.getBillById(billId).toDomain()
    }

    override suspend fun updateBillBalance(billId: String, balanceChange: Long) {
        billApi.updateBillBalance(billId, TransactionCreationDto(balanceChange))
    }

    override suspend fun closeBill(billId: String) {
        billApi.closeBill(billId)
    }

    override suspend fun getBillHistory(billId: String): List<BillHistoryItem> {
        return billApi.getBillHistory(billId).map { it.toDomain() }
    }
}
