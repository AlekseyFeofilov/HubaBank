package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class FetchCreditPaymentsUseCase @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource,
    private val creditLocalDataSource: CreditLocalDataSource,
) : SimpleUseCase<String, Unit> {

    override suspend fun execute(param: String) {
        val creditPayments = creditRemoteDataSource.getAllCreditPayment(param)
        creditLocalDataSource.saveCreditPayments(param, creditPayments)
    }
}
