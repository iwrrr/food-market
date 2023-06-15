package com.hwaryun.datasource.repository

import androidx.paging.PagingData
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.http.infrastructure.execute
import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.datasource.paging.createPager
import com.hwaryun.network.FoodMarketApi
import com.hwaryun.network.model.response.FoodDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodMarketApi: FoodMarketApi
) : FoodRepository {

    override fun getFoods(
        types: String?
    ): Flow<PagingData<FoodDto>> = createPager { page ->
        foodMarketApi.fetchFoods(page = page, types = types).data?.results
    }.flow

    override fun getFoodById(
        id: Int
    ): Flow<NetworkClientResult<BaseResponse<FoodDto>>> = flow {
        emit(execute { foodMarketApi.fetchFoodById(id) })
    }
}