package ru.hits.hubabank.domain.credit

import ru.hits.hubabank.domain.core.SimpleUseCase
import ru.hits.hubabank.domain.credit.model.CreateCredit
import javax.inject.Inject

class AddNewCreditUseCase @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource,
) : SimpleUseCase<CreateCredit, Unit> {

    override suspend fun execute(param: CreateCredit) {
        creditRemoteDataSource.createNewCredit(param)
    }
}
