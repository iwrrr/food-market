package com.hwaryun.common.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<P, R : Any> constructor(private val dispatcher: CoroutineDispatcher) {

    abstract fun buildFlowUseCase(param: P? = null): Flow<R>

    operator fun invoke(params: P? = null): Flow<R> {
        return buildFlowUseCase(params).flowOn(dispatcher)
    }
}

class ConnectivityException : Throwable()