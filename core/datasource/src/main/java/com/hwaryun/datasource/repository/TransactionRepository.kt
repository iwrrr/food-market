package com.hwaryun.datasource.repository

import androidx.paging.PagingData
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.network.model.response.TransactionDto
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun checkoutFood(
        foodId: Int,
        userId: Int,
        qty: Int,
        total: Int
    ): Flow<NetworkClientResult<BaseResponse<TransactionDto>>>

    fun getTransactions(
        status: String? = null
    ): Flow<PagingData<TransactionDto>>

    fun getTransactionDetail(
        id: Int
    ): Flow<NetworkClientResult<BaseResponse<TransactionDto>>>

    fun cancelOrder(
        id: Int
    ): Flow<NetworkClientResult<BaseResponse<TransactionDto>>>
}

