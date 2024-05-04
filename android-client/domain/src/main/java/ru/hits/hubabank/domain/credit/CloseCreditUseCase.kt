package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class CloseCreditUseCase @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource,
    private val creditLocalDataSource: CreditLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        creditRemoteDataSource.deleteCredit(param)
        creditLocalDataSource.deleteCredit(param)
    }
}
