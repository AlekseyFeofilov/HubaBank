package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.ChangeBillBalanceModel
import ru.hits.hubabank.domain.bill.model.NewTransactionType
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ChangeBillBalanceUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
) : SimpleUseCase<ChangeBillBalanceModel, Unit> {

    override suspend fun execute(param: ChangeBillBalanceModel) {
        when (param.newTransactionType) {
            NewTransactionType.DEPOSIT -> {
                billRemoteDataSource.giveMoneyForBill(param.billId, param.balanceChange)
            }
            NewTransactionType.WITHDRAWAL -> {
                billRemoteDataSource.withdrawMoneyFromBill(param.billId, param.balanceChange)
            }
            NewTransactionType.TO_BILL -> {
                billRemoteDataSource.transferMoneyToBill(param.billId, param.balanceChange, param.targetBill)
            }
        }
    }
}
