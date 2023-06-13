package com.hwaryun.domain.mapper

import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.User
import com.hwaryun.network.model.response.AuthDto
import com.hwaryun.network.model.response.FoodDto

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

fun FoodDto.FoodItemDto?.toFood(): Food {
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