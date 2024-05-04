package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.credit.model.CreateCredit
import ru.hits.hubabank.domain.credit.model.Credit
import ru.hits.hubabank.domain.credit.model.CreditPayment
import ru.hits.hubabank.domain.credit.model.CreditTerms

interface CreditRemoteDataSource {

    suspend fun createNewCredit(createCredit: CreateCredit)

    suspend fun getAllCredits(): List<Credit>

    suspend fun getCreditById(creditId: String): Credit

    suspend fun getAllCreditTerms(): List<CreditTerms>

    suspend fun getAllCreditPayment(creditId: String): List<CreditPayment>

    suspend fun deleteCredit(creditId: String)
}
