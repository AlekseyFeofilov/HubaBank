package ru.hits.hubabank.domain.bill

import ru.hits.hubabank.domain.bill.model.ChangeBillHiddenModel
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ChangeBillHiddenUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<ChangeBillHiddenModel, Unit> {

    override suspend fun execute(param: ChangeBillHiddenModel) {
        billRemoteDataSource.saveHiddenMode(param.billId, param.isHidden)
        billLocalDataSource.changeBillHidden(param.billId, param.isHidden)
    }
}
