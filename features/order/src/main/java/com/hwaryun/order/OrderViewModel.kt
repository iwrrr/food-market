package com.hwaryun.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hwaryun.datasource.repository.TransactionRepository
import com.hwaryun.domain.mapper.toTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    repository: TransactionRepository
) : ViewModel() {

    val inProgressOrders = repository.getTransactions(IN_PROGRESS)
        .map { pagingData ->
            pagingData.map {
                it.toTransaction()
            }
        }
        .cachedIn(viewModelScope)

    val postOrders = repository.getTransactions(POST_ORDERS)
        .map { pagingData ->
            pagingData.map {
                it.toTransaction()
            }
        }
        .cachedIn(viewModelScope)

    companion object {
        private const val ON_DELIVERY = "ON_DELIVERY"
        private const val DELIVERED = "DELIVERED"
        private const val CANCELLED = "CANCELLED"
        private const val IN_PROGRESS = "ON_DELIVERY"
        private const val POST_ORDERS = "DELIVERED,CANCELLED"
    }
}