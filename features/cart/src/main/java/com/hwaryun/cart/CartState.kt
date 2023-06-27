package com.hwaryun.cart

import com.hwaryun.domain.model.Cart
import com.hwaryun.domain.model.Transaction
import com.hwaryun.domain.model.User

data class CartState(
    val cart: Cart? = null,
    val transaction: Transaction? = null,
    val user: User? = null,
    val qty: Int = 1,
    val serviceFee: Int = 5000,
    val shippingCost: Int = 10000,
    val totalFoodPrice: Int = 0,
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
    val error: String = "",
)