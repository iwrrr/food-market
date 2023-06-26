package com.hwaryun.datasource.repository

import androidx.paging.PagingData
import com.hwaryun.common.ext.execute
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.datasource.paging.createPager
import com.hwaryun.network.FoodMarketApi
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.PagingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodMarketApi: FoodMarketApi
) : FoodRepository {

    override fun getFoods(
        name: String?
    ): Flow<PagingData<FoodDto>> = createPager { page ->
        foodMarketApi.fetchFoods(page = page, name = name).data?.results
    }.flow

    override fun getTrendingFoods(types: String): Flow<DataResult<BaseResponse<PagingDto<FoodDto>>>> =
        flow {
            emit(execute { foodMarketApi.fetchFoods(types = types) })
        }

    override fun getFoodById(
        id: Int
    ): Flow<DataResult<BaseResponse<FoodDto>>> = flow {
        emit(execute { foodMarketApi.fetchFoodById(id) })
    }
}