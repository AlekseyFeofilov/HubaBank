package ru.hits.hubabank.domain.user

import ru.hits.hubabank.domain.auth.AuthLocalDataSource
import ru.hits.hubabank.domain.bill.BillLocalDataSource
import ru.hits.hubabank.domain.core.SimpleUseCase
import javax.inject.Inject

class ExitUseCase @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val billLocalDataSource: BillLocalDataSource,
) : SimpleUseCase<Unit, Unit> {

    override suspend fun execute(param: Unit) {
        authLocalDataSource.clearTokens()
        userLocalDataSource.deleteProfile()
        billLocalDataSource.deleteAllData()
    }
}
