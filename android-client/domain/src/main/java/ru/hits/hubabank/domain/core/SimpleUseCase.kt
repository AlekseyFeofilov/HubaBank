package ru.hits.hubabank.domain.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SimpleUseCase<in Input, Output> {

    suspend operator fun invoke(param: Input): Result<Output> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(execute(param))
            } catch (exception: Exception) {
                println(exception.message)
                Result.failure(exception)
            }
        }
    }

    suspend fun execute(param: Input): Output
}
