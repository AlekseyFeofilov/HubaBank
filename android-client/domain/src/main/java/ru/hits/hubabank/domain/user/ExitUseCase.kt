package ru.hits.hubabank.domain.user

import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import ru.hits.hubabank.domain.bill.BillLocalDataSource
import ru.hits.hubabank.domain.core.SimpleUseCase
import ru.hits.hubabank.domain.credit.CreditLocalDataSource
import javax.inject.Inject

class ExitUseCase @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val billLocalDataSource: BillLocalDataSource,
    private val creditLocalDataSource: CreditLocalDataSource,
) : SimpleUseCase<Unit, Unit> {

    override suspend fun execute(param: Unit) {
        userLocalDataSource.deleteProfile()
        billLocalDataSource.deleteAllData()
        creditLocalDataSource.deleteAllCredits()
        try {
            userRemoteDataSource.logout()
        } catch (e: Exception) {
            println("logout server error" + e.message)
        }
        authLocalDataSource.clearTokens()
    }
}
