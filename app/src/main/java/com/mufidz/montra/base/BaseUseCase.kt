package com.mufidz.montra.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<RequestParam, ResponseObject, UseCaseResult>
    (private val dispatcher: CoroutineDispatcher) {

    protected abstract suspend fun execute(param: RequestParam): ResponseObject

    protected abstract suspend fun ResponseObject.transformToUseCaseResult(): UseCaseResult

    suspend fun getResult(param: RequestParam): UseCaseResult {
        val executionResult = withContext(dispatcher) {
            execute(param)
        }
        return executionResult.transformToUseCaseResult()
    }
}