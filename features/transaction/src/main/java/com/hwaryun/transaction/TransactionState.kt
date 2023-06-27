package com.hwaryun.transaction

data class TransactionState(
    val filter: String = "",
    val filterAllSelected: Boolean = true,
    val filterOnDeliverySelected: Boolean = false,
    val filterDeliveredSelected: Boolean = false,
    val filterCancelledSelected: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
