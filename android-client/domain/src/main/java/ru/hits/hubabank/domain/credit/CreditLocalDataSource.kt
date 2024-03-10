package ru.hits.hubabank.domain.credit

import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.domain.credit.model.Credit

interface CreditLocalDataSource {

    suspend fun saveCredits(credits: List<Credit>)

    suspend fun saveCredit(credit: Credit)

    fun observeAllCredits(): Flow<List<Credit>>

    fun observeCreditById(creditId: String): Flow<Credit>

    suspend fun deleteCredit(creditId: String)

    suspend fun deleteAllCredits()
}
