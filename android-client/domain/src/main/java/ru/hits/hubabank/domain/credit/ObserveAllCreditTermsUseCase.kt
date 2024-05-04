package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.core.ObservingUseCase
import ru.hits.hubabank.domain.credit.model.CreditTerms
import javax.inject.Inject

class ObserveAllCreditTermsUseCase @Inject constructor(
    private val creditLocalDataSource: CreditLocalDataSource,
) : ObservingUseCase<Unit, List<CreditTerms>> {

    override fun execute(param: Unit): Flow<Result<List<CreditTerms>>> {
        return creditLocalDataSource.observeAllCreditTerms().map { Result.success(it) }
    }
}
