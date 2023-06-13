package com.hwaryun.common.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<P, R : Any> constructor(private val dispatcher: CoroutineDispatcher) {

    abstract suspend fun buildFlowUseCase(param: P? = null): Flow<R>

    suspend fun execute(params: P? = null): Flow<R> {
        return buildFlowUseCase(params).flowOn(dispatcher)
    }
}

class ConnectivityException : Throwable()