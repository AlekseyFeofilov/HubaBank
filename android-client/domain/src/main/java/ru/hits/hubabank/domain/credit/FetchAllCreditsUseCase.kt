package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchAllCreditsUseCase @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource,
    private val creditLocalDataSource: CreditLocalDataSource,
) : SimpleUseCase<Unit, Unit> {

    override suspend fun execute(param: Unit) {
        val bills = creditRemoteDataSource.getAllCredits()
        creditLocalDataSource.saveCredits(bills)
    }
}
