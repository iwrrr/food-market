package com.hwaryun.transaction_detail

import com.hwaryun.domain.model.Transaction

data class TransactionState(
    val transaction: Transaction? = null,
    val isLoading: Boolean = false,
    val error: String = "",
)
