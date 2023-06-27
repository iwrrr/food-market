package com.hwaryun.transaction_detail

data class CancelOrderState(
    val isCancelled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
