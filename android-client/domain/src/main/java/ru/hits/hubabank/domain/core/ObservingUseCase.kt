package ru.hits.hubabank.domain.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

interface ObservingUseCase<in Input, Output> {

    operator fun invoke(param: Input): Flow<Result<Output>> {
        return execute(param)
            .catch { error ->
                println(error.message)
                emit(Result.failure(error))
            }.flowOn(Dispatchers.IO)
    }

    fun execute(param: Input): Flow<Result<Output>>
}
