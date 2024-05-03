package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class StartObserveBillHistoryUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
) : SimpleUseCase<String, Flow<BillHistoryItem>> {

    override suspend fun execute(param: String): Flow<BillHistoryItem> {
        return billRemoteDataSource.startObserveBillHistory(param)
    }
}
