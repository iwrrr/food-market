package com.hwaryun.payment

import com.hwaryun.domain.model.Transaction

data class TransactionUiState(
    val transaction: Transaction? = null,
    val isCancelled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
