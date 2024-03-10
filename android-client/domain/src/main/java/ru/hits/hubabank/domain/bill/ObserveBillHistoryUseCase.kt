package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.domain.core.ObservingUseCase
import java.time.LocalDate
import javax.inject.Inject

class ObserveBillHistoryUseCase @Inject constructor(
    private val billLocalDataSource: BillLocalDataSource,
) : ObservingUseCase<String, Map<LocalDate, List<BillHistoryItem>>> {

    override fun execute(param: String): Flow<Result<Map<LocalDate, List<BillHistoryItem>>>> {
        return billLocalDataSource.observeBillHistory(param)
            .map { list -> list.groupBy { it.dateTime.toLocalDate() } }
            .map { Result.success(it) }
    }
}
