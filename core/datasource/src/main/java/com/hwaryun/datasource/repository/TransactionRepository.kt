package com.hwaryun.datasource.repository

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
}

