package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.ChangeBillBalanceModel
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ChangeBillBalanceUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<ChangeBillBalanceModel, Unit> {

    override suspend fun execute(param: ChangeBillBalanceModel) {
        billRemoteDataSource.updateBillBalance(param.billId, param.balanceChange)
        billLocalDataSource.updateBillBalance(param.billId, param.balanceChange)
    }
}
