package com.hwaryun.payment

import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.Transaction
import com.hwaryun.domain.model.User

data class PaymentUiState(
    val food: Food? = null,
    val transaction: Transaction? = null,
    val user: User? = null,
    val qty: Int = 1,
    val tax: Int = 0,
    val shippingCost: Int = 0,
    val totalFoodPrice: Int = 0,
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
    val error: String = "",
)
