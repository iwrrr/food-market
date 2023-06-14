package com.hwaryun.payment

import com.hwaryun.domain.model.Transaction

data class TransactionUiState(
    val transaction: Transaction? = null,
    val isLoading: Boolean = false,
    val error: String = "",
)
