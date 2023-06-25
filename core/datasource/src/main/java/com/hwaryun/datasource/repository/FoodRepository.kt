package com.hwaryun.datasource.repository

import androidx.paging.PagingData
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.PagingDto
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoods(
        types: String? = null
    ): Flow<PagingData<FoodDto>>

    fun getTrendingFoods(types: String): Flow<NetworkClientResult<BaseResponse<PagingDto<FoodDto>>>>

    fun getFoodById(
        id: Int
    ): Flow<NetworkClientResult<BaseResponse<FoodDto>>>
}