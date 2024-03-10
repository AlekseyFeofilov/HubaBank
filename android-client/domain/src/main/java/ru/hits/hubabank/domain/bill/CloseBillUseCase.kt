package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class CloseBillUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        billRemoteDataSource.closeBill(param)
        billLocalDataSource.deleteBill(param)
    }
}
