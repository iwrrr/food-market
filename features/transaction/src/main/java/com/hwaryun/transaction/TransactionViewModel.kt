package com.hwaryun.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hwaryun.datasource.repository.TransactionRepository
import com.hwaryun.domain.mapper.toTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    repository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionState())
    val state = _state.asStateFlow()

    val transactions = _state.debounce(300L).flatMapLatest { state ->
        repository.getTransactions(status = state.filter)
            .map { pagingData ->
                pagingData.map {
                    it.toTransaction()
                }
            }
            .cachedIn(viewModelScope)
    }.catch { e ->
        _state.update {
            it.copy(error = e.message.toString())
        }
    }

    fun filterAll() {
        _state.update {
            it.copy(
                filter = "",
                filterAllSelected = true,
                filterOnDeliverySelected = false,
                filterDeliveredSelected = false,
                filterCancelledSelected = false
            )
        }
    }

    fun filterOnDelivery() {
        _state.update {
            it.copy(
                filter = ON_DELIVERY,
                filterAllSelected = false,
                filterOnDeliverySelected = true,
                filterDeliveredSelected = false,
                filterCancelledSelected = false
            )
        }
    }

    fun filterDelivered() {
        _state.update {
            it.copy(
                filter = DELIVERED,
                filterAllSelected = false,
                filterOnDeliverySelected = false,
                filterDeliveredSelected = true,
                filterCancelledSelected = false
            )
        }
    }

    fun filterCancelled() {
        _state.update {
            it.copy(
                filter = CANCELLED,
                filterAllSelected = false,
                filterOnDeliverySelected = false,
                filterDeliveredSelected = false,
                filterCancelledSelected = true
            )
        }
    }

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }

    companion object {
        private const val ON_DELIVERY = "ON_DELIVERY"
        private const val DELIVERED = "DELIVERED"
        private const val CANCELLED = "CANCELLED"
    }
}