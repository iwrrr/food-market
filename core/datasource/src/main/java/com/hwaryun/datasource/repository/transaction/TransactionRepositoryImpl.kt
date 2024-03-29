package com.hwaryun.datasource.repository.transaction

import androidx.paging.PagingData
import com.hwaryun.common.ext.execute
import com.hwaryun.common.http.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.datasource.paging.createPager
import com.hwaryun.network.FoodMarketApi
import com.hwaryun.network.model.request.CheckoutRequest
import com.hwaryun.network.model.response.TransactionDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val foodMarketApi: FoodMarketApi
) : TransactionRepository {

    override fun checkoutFood(
        foodId: Int,
        userId: Int,
        qty: Int,
        total: Int
    ): Flow<DataResult<BaseResponse<TransactionDto>>> = flow {
        val body = CheckoutRequest(
            foodId = foodId,
            userId = userId,
            qty = qty,
            total = total
        )

        emit(execute { foodMarketApi.checkout(body) })
    }

    override fun getTransactions(status: String?): Flow<PagingData<TransactionDto>> =
        createPager { page ->
            foodMarketApi.fetchTransactions(page = page, status = status).data?.results
        }.flow

    override fun getTransactionDetail(id: Int): Flow<DataResult<BaseResponse<TransactionDto>>> =
        flow {
            emit(execute { foodMarketApi.fetchTransactionById(id) })
        }

    override fun cancelOrder(id: Int): Flow<DataResult<BaseResponse<TransactionDto>>> =
        flow {
            emit(execute { foodMarketApi.cancelOrder(id, "CANCELLED") })
        }
}