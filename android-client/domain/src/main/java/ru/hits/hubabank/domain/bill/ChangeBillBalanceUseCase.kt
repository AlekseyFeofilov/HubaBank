package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.BillChangeReason
import ru.hits.hubabank.domain.bill.model.ChangeBillBalanceModel
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ChangeBillBalanceUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<ChangeBillBalanceModel, Unit> {

    override suspend fun execute(param: ChangeBillBalanceModel) {
        if (param.changeReason == BillChangeReason.TERMINAL) {
            billRemoteDataSource.giveMoneyForBill(param.billId, param.balanceChange)
        }
        if (param.changeReason == BillChangeReason.USER) {
            billRemoteDataSource.transferMoneyToBill(param.billId, param.balanceChange, param.targetBill)
        }
        billLocalDataSource.updateBillBalance(
            param.billId,
            if (param.changeReason == BillChangeReason.USER) -param.balanceChange else param.balanceChange
        )
    }
}
