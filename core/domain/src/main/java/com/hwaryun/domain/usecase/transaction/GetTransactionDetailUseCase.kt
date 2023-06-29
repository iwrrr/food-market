package com.hwaryun.domain.usecase.transaction

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.transaction.TransactionRepository
import com.hwaryun.domain.mapper.toTransaction
import com.hwaryun.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionDetailUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, UiResult<Transaction>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Int?): Flow<UiResult<Transaction>> = flow {
        emit(UiResult.Loading())
        param?.let { foodId ->
            transactionRepository.getTransactionDetail(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        result.value?.data?.let {
                            emit(UiResult.Success(it.toTransaction()))
                        }
                    },
                    doOnError = {
                        emit(UiResult.Failure(it.throwable))
                    }
                )
            }
        }
    }
}