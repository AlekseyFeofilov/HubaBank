package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.core.ObservingUseCase
import ru.hits.hubabank.domain.credit.model.CreditPayment
import javax.inject.Inject

class ObserveCreditPaymentsUseCase @Inject constructor(
    private val creditLocalDataSource: CreditLocalDataSource,
) : ObservingUseCase<String, List<CreditPayment>> {

    override fun execute(param: String): Flow<Result<List<CreditPayment>>> {
        return creditLocalDataSource.observeCreditPayments(param).map { Result.success(it) }
    }
}
