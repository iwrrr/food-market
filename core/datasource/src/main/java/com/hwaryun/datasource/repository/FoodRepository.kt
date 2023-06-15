package com.hwaryun.datasource.repository

import androidx.paging.PagingData
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.network.model.response.FoodDto
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoods(
        types: String? = null
    ): Flow<PagingData<FoodDto>>

    fun getFoodById(
        id: Int
    ): Flow<NetworkClientResult<BaseResponse<FoodDto>>>
}