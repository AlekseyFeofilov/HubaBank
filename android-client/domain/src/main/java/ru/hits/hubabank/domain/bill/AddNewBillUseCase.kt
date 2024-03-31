package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.Currency
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class AddNewBillUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<Currency, Unit> {

    override suspend fun execute(param: Currency) {
        val newBill = billRemoteDataSource.createNewBill(param)
        billLocalDataSource.saveBill(newBill)
    }
}
