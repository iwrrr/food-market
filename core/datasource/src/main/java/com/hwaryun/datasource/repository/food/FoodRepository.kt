package com.hwaryun.datasource.repository.food

import androidx.paging.PagingData
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.PagingDto
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoods(
        name: String? = null
    ): Flow<PagingData<FoodDto>>

    fun getTrendingFoods(types: String): Flow<DataResult<BaseResponse<PagingDto<FoodDto>>>>

    fun getFoodById(
        id: Int
    ): Flow<DataResult<BaseResponse<FoodDto>>>
}