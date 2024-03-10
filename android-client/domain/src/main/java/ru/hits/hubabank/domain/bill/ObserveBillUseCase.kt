package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.core.ObservingUseCase
import javax.inject.Inject

class ObserveBillUseCase @Inject constructor(
    private val billLocalDataSource: BillLocalDataSource,
) : ObservingUseCase<String, Bill> {

    override fun execute(param: String): Flow<Result<Bill>> {
        return billLocalDataSource.observeBillById(param).map { Result.success(it) }
    }
}
