package ru.hits.hubabank.data.database.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.data.database.credit.model.toDomain
import ru.hits.hubabank.data.database.credit.model.toEntity
import ru.hits.hubabank.domain.credit.CreditLocalDataSource
import ru.hits.hubabank.domain.credit.model.Credit
import javax.inject.Inject

internal class CreditLocalDataSourceImpl @Inject constructor(
    private val creditDao: CreditDao,
) : CreditLocalDataSource {

    override suspend fun saveCredits(credits: List<Credit>) {
        val oldCredits = creditDao.observeAllCredits().first()
        val creditsForDelete = oldCredits.filter { old -> credits.none { it.id == old.id} }
        creditDao.insertCredits(credits.map { it.toEntity() })
        creditDao.deleteCredits(creditsForDelete.map { it.id })
    }

    override suspend fun saveCredit(credit: Credit) {
        creditDao.insertCredit(credit.toEntity())
    }

    override fun observeAllCredits(): Flow<List<Credit>> {
        return creditDao.observeAllCredits().map { list -> list.map { it.toDomain() } }
    }

    override fun observeCreditById(creditId: String): Flow<Credit> {
        return creditDao.observeCredit(creditId).map { it.toDomain() }
    }

    override suspend fun deleteCredit(creditId: String) {
        creditDao.deleteCredit(creditId)
    }

    override suspend fun deleteAllCredits() {
        creditDao.deleteAllCredits()
    }
}
