package com.hwaryun.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlists")
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val foodId: Int,
    val description: String,
    val ingredients: String,
    val name: String,
    val picturePath: String,
    val price: Int,
    val rate: Float,
    val types: String
)
