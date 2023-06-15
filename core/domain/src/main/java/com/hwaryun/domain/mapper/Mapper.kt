package com.hwaryun.domain.mapper

import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.Transaction
import com.hwaryun.domain.model.User
import com.hwaryun.network.model.response.AuthDto
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.TransactionDto

fun AuthDto.UserDto?.toUser(): User {
    return User(
        address = this?.address.orDash(),
        city = this?.city.orDash(),
        email = this?.email.orDash(),
        houseNumber = this?.houseNumber.orDash(),
        id = this?.id.orZero(),
        name = this?.name.orDash(),
        phoneNumber = this?.phoneNumber.orDash(),
        profilePhotoPath = this?.profilePhotoPath.orDash(),
        profilePhotoUrl = this?.profilePhotoUrl.orDash(),
    )
}

fun FoodDto?.toFood(): Food {
    return Food(
        description = this?.description.orDash(),
        id = this?.id.orZero(),
        ingredients = this?.ingredients.orDash(),
        name = this?.name.orDash(),
        picturePath = this?.picturePath.orDash(),
        price = this?.price.orZero(),
        rate = this?.rate.orDash(),
        types = this?.types.orDash(),
    )
}

fun TransactionDto?.toTransaction(): Transaction {
    val status = when (this?.status) {
        "CANCELLED" -> "Cancelled"
        "ON_DELIVERY" -> "On Delivery"
        "DELIVERED" -> "Delivered"
        else -> "-"
    }

    return Transaction(
        createdAt = this?.createdAt.orZero(),
        deletedAt = this?.deletedAt.orZero(),
        foodId = this?.foodId.orZero(),
        id = this?.id.orZero(),
        paymentUrl = this?.paymentUrl.orDash(),
        quantity = this?.quantity.orDash(),
        status = status,
        total = this?.total.orZero(),
        updatedAt = this?.updatedAt.orZero(),
        userId = this?.userId.orZero(),
        food = this?.food.toFood(),
        user = this?.user.toUser()
    )
}