package com.hwaryun.cart

import com.hwaryun.domain.model.Transaction

data class TransactionState(
    val transaction: Transaction? = null,
    val isCancelled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
