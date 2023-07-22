package com.hwaryun.datasource.repository.transaction

import androidx.paging.PagingData
import com.hwaryun.common.http.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.network.model.response.TransactionDto
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun checkoutFood(
        foodId: Int,
        userId: Int,
        qty: Int,
        total: Int
    ): Flow<DataResult<BaseResponse<TransactionDto>>>

    fun getTransactions(
        status: String? = null
    ): Flow<PagingData<TransactionDto>>

    fun getTransactionDetail(
        id: Int
    ): Flow<DataResult<BaseResponse<TransactionDto>>>

    fun cancelOrder(
        id: Int
    ): Flow<DataResult<BaseResponse<TransactionDto>>>
}

