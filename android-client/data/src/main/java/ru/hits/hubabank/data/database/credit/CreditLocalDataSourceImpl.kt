package ru.hits.hubabank.data.database.credit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.data.database.credit.model.toDomain
import ru.hits.hubabank.data.database.credit.model.toEntity
import ru.hits.hubabank.domain.credit.CreditLocalDataSource
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.CreditTerms
import javax.inject.Inject

internal class CreditLocalDataSourceImpl @Inject constructor(
    private val creditDao: CreditDao,
    private val creditTermsAndPaymentDao: CreditTermsAndPaymentDao,
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

    override suspend fun saveCreditTerms(creditTerms: List<CreditTerms>) {
        val oldCreditTerms = creditTermsAndPaymentDao.observeAllCreditsTerms().first()
        val creditTermsForDelete = oldCreditTerms.filter { old -> creditTerms.none { it.id == old.id} }
        creditTermsAndPaymentDao.insertCreditTerms(creditTerms.map { it.toEntity() })
        creditTermsAndPaymentDao.deleteCreditTerms(creditTermsForDelete.map { it.id })
    }

    override fun observeAllCreditTerms(): Flow<List<CreditTerms>> {
        return creditTermsAndPaymentDao.observeAllCreditsTerms().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun saveCreditPayments(creditId: String, creditPayments: List<CreditPayment>) {
        val oldCreditPayments = creditTermsAndPaymentDao.observeAllCreditsTerms().first()
        val creditPaymentsForDelete = oldCreditPayments.filter { old -> creditPayments.none { it.id == old.id} }
        creditTermsAndPaymentDao.insertCreditPayments(creditPayments.map { it.toEntity() })
        creditTermsAndPaymentDao.deleteCreditPayments(creditId, creditPaymentsForDelete.map { it.id })
    }

    override fun observeCreditPayments(creditId: String): Flow<List<CreditPayment>> {
        return creditTermsAndPaymentDao.observeCreditPayments(creditId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteCredit(creditId: String) {
        creditDao.deleteCredit(creditId)
        val creditPayment = creditTermsAndPaymentDao.observeCreditPayments(creditId).first()
        creditTermsAndPaymentDao.deleteCreditPayments(creditId, creditPayment.map { it.id })
    }

    override suspend fun deleteAllCreditsAndTermsAndPayments() {
        creditDao.deleteAllCredits()
        creditTermsAndPaymentDao.deleteAllCreditTerms()
        creditTermsAndPaymentDao.deleteAllCreditPayments()
    }
}
