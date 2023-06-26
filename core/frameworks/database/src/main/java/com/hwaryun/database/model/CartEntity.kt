package com.hwaryun.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val picturePath: String = "",
    val price: Int = 0,
)
