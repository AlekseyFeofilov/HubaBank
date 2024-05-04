package ru.hits.hubabank.data.network.credit

import kotlinx.coroutines.flow.first
import ru.hits.hubabank.data.database.credit.CreditDao
import ru.hits.hubabank.data.database.credit.model.toDomain
import ru.hits.hubabank.domain.credit.CreditRemoteDataSource
import ru.hits.hubabank.domain.credit.model.CreateCredit
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.CreditTerms
import javax.inject.Inject

internal class CreditRemoteDataSourceMockImpl @Inject constructor(
    private val creditDao: CreditDao,
) : CreditRemoteDataSource {

    override suspend fun createNewCredit(createCredit: CreateCredit) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCredits(): List<Credit> {
        return creditDao.observeAllCredits().first().map { it.toDomain() }
    }

    override suspend fun getCreditById(creditId: String): Credit {
        return creditDao.observeCredit(creditId).first().toDomain()
    }

    override suspend fun getAllCreditTerms(): List<CreditTerms> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCreditPayment(creditId: String): List<CreditPayment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCredit(creditId: String) {
        TODO("Not yet implemented")
    }
}
