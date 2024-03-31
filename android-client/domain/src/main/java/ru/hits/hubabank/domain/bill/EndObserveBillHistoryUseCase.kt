package ru.hits.hubabank.domain.bill

import javax.inject.Inject

class EndObserveBillHistoryUseCase @Inject constructor(
    private val billRemoteDataSource: BillRemoteDataSource,
) {
    operator fun invoke() {
        billRemoteDataSource.endObserveBillHistory()
    }
}