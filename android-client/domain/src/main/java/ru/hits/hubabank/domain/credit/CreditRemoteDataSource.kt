package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.credit.model.Credit

interface CreditRemoteDataSource {

    suspend fun createNewCredit(): Credit

    suspend fun getAllCredits(): List<Credit>

    suspend fun getCreditById(creditId: String): Credit

    suspend fun closeCredit(creditId: String)
}
