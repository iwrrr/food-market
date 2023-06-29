package com.hwaryun.domain.usecase.food

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.food.FoodRepository
import com.hwaryun.domain.mapper.toFood
import com.hwaryun.domain.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFoodDetailUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, UiResult<Food>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Int?): Flow<UiResult<Food>> = flow {
        emit(UiResult.Loading())
        param?.let { foodId ->
            foodRepository.getFoodById(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        emit(UiResult.Success(result.value?.data.toFood()))
                    },
                    doOnError = {
                        emit(UiResult.Failure(it.throwable))
                    }
                )
            }
        }
    }
}