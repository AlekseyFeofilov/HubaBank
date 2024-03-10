package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchCreditUseCase @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource,
    private val creditLocalDataSource: CreditLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        val credit = creditRemoteDataSource.getCreditById(param)
        creditLocalDataSource.saveCredit(credit)
    }
}
