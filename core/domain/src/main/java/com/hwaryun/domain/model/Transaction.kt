package com.hwaryun.domain.model

data class Transaction(
    val createdAt: Long,
    val deletedAt: Long,
    val foodId: Int,
    val id: Int,
    val paymentUrl: String,
    val quantity: String,
    val status: String,
    val total: Int,
    val updatedAt: Long,
    val userId: Int,
    val food: Food,
    val user: User
)