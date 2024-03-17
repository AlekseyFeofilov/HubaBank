package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.core.ObservingUseCase
import ru.hits.hubabank.domain.credit.model.Credit
import javax.inject.Inject

class ObserveAllCreditsUseCase @Inject constructor(
    private val creditLocalDataSource: CreditLocalDataSource,
) : ObservingUseCase<Unit, List<Credit>> {

    override fun execute(param: Unit): Flow<Result<List<Credit>>> {
        return creditLocalDataSource.observeAllCredits().map { Result.success(it) }
    }
}
