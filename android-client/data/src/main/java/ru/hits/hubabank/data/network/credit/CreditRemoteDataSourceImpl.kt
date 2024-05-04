package ru.hits.hubabank.data.network.credit

import ru.hits.hubabank.data.network.credit.model.toDomain
import ru.hits.hubabank.data.network.credit.model.toDto
import ru.hits.hubabank.domain.credit.CreditRemoteDataSource
import ru.hits.hubabank.domain.credit.model.CreateCredit
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.CreditTerms
import javax.inject.Inject

internal class CreditRemoteDataSourceImpl @Inject constructor(
    private val creditApi: CreditApi,
) : CreditRemoteDataSource {

    override suspend fun createNewCredit(createCredit: CreateCredit) {
        creditApi.createCredit(createCredit.toDto())
    }

    override suspend fun getAllCredits(): List<Credit> {
        return creditApi.getAllCredit().map { it.toDomain() }
    }

    override suspend fun getCreditById(creditId: String): Credit {
        return creditApi.getCreditById(creditId).toDomain()
    }

    override suspend fun getAllCreditTerms(): List<CreditTerms> {
        return creditApi.getAllCreditTerms().filterNot { it.isDeleted }.map { it.toDomain() }
    }

    override suspend fun getAllCreditPayment(creditId: String): List<CreditPayment> {
        return creditApi.getAllCreditPayment(creditId).map { it.toDomain() }
    }

    override suspend fun deleteCredit(creditId: String) {
        creditApi.deleteCredit(creditId)
    }
}
