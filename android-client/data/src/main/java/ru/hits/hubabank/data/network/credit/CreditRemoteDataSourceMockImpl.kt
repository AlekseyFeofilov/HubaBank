package ru.hits.hubabank.data.network.credit

import kotlinx.coroutines.flow.first
import ru.hits.hubabank.data.database.credit.CreditDao
import ru.hits.hubabank.data.database.credit.model.toDomain
import ru.hits.hubabank.domain.credit.CreditRemoteDataSource
import ru.hits.hubabank.domain.credit.model.Credit
import java.time.LocalDateTime
import javax.inject.Inject

internal class CreditRemoteDataSourceMockImpl @Inject constructor(
    private val creditDao: CreditDao,
) : CreditRemoteDataSource {

    override suspend fun createNewCredit(): Credit {
        return Credit(LocalDateTime.now().toString(), 100000000, 10000)
    }

    override suspend fun getAllCredits(): List<Credit> {
        return creditDao.observeAllCredits().first().map { it.toDomain() }
    }

    override suspend fun getCreditById(creditId: String): Credit {
        return creditDao.observeCredit(creditId).first().toDomain()
    }

    override suspend fun closeCredit(creditId: String) { }
}
