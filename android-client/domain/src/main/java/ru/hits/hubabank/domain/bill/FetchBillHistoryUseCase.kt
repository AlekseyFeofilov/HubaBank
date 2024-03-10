package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchBillHistoryUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        val billHistoryItems = billRemoteDataSource.getBillHistory(param)
        billLocalDataSource.saveBillHistory(billHistoryItems)
    }
}
