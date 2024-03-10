package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchBillUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        val bill = billRemoteDataSource.getBillById(param)
        billLocalDataSource.saveBill(bill)
    }
}
