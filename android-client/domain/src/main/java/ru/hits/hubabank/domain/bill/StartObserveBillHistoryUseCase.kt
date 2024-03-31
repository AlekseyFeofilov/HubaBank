package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import javax.inject.Inject

class StartObserveBillHistoryUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
) {

    operator fun invoke(billId: String): Flow<BillHistoryItem> {
        return billRemoteDataSource.startObserveBillHistory(billId)
    }
}
