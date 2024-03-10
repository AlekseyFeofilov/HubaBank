package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.core.ObservingUseCase
import ru.hits.hubabank.domain.credit.model.Credit
import javax.inject.Inject

class ObserveCreditUseCase @Inject constructor(
    private val creditLocalDataSource: CreditLocalDataSource,
) : ObservingUseCase<String, Credit> {

    override fun execute(param: String): Flow<Result<Credit>> {
        return creditLocalDataSource.observeCreditById(param).map { Result.success(it) }
    }
}
