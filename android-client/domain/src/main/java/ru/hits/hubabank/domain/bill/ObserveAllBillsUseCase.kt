package ru.hits.hubabank.domain.bill

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.domain.core.ObservingUseCase
import javax.inject.Inject

class ObserveAllBillsUseCase @Inject constructor(
    private val billLocalDataSource: BillLocalDataSource,
) : ObservingUseCase<Unit, List<Bill>> {

    override fun execute(param: Unit): Flow<Result<List<Bill>>> {
        return billLocalDataSource.observeAllBills().map { Result.success(it) }
    }
}
