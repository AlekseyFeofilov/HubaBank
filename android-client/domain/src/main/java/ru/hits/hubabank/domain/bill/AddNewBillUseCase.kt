package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class AddNewBillUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<Unit, Unit> {

    override suspend fun execute(param: Unit) {
        val newBill = billRemoteDataSource.createNewBill()
        billLocalDataSource.saveBill(newBill)
    }
}
