package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.CreditTerms

interface CreditLocalDataSource {

    suspend fun saveCredits(credits: List<Credit>)

    suspend fun saveCredit(credit: Credit)

    fun observeAllCredits(): Flow<List<Credit>>

    fun observeCreditById(creditId: String): Flow<Credit>

    suspend fun saveCreditTerms(creditTerms: List<CreditTerms>)

    fun observeAllCreditTerms(): Flow<List<CreditTerms>>

    suspend fun saveCreditPayments(creditId: String, creditPayments: List<CreditPayment>)

    fun observeCreditPayments(creditId: String): Flow<List<CreditPayment>>

    suspend fun deleteCredit(creditId: String)

    suspend fun deleteAllCreditsAndTermsAndPayments()
}
