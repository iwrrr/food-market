package com.hwaryun.domain.usecase.wishlist

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.wishlist.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val userPreferenceManager: UserPreferenceManager,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, UiResult<Boolean>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Int?): Flow<UiResult<Boolean>> = flow {
        param?.let { foodId ->
            val userId = userPreferenceManager.user.firstOrNull()?.id
            userId?.let {
                wishlistRepository.getWishlist(foodId, it).collect { dataResult ->
                    dataResult.suspendSubscribe(
                        doOnSuccess = {
                            dataResult.value?.let { data ->
                                emit(UiResult.Success(data.foodId == foodId))
                            }
                        },
                        doOnError = {
                            emit(UiResult.Failure(dataResult.throwable))
                        }
                    )

                }
            }
        }
    }
}