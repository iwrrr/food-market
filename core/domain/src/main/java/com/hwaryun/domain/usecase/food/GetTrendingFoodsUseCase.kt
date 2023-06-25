package com.hwaryun.domain.usecase.food

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.FoodRepository
import com.hwaryun.domain.mapper.toFood
import com.hwaryun.domain.model.Food
import com.hwaryun.network.model.response.FoodDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<List<Food>>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<List<Food>>> = flow {
        emit(UiResult.Loading())
        foodRepository.getTrendingFoods(POPULAR).collect { result ->
            result.suspendSubscribe(
                doOnSuccess = {
                    result.value?.data?.results?.let { foods ->
                        if (foods.isEmpty()) {
                            emit(UiResult.Empty())
                        } else {
                            emit(UiResult.Success(foods.map(FoodDto::toFood)))
                        }
                    }
                },
                doOnError = {
                    emit(UiResult.Failure(it.throwable))
                }
            )
        }
    }

    private companion object {
        const val POPULAR = "popular"
    }
}