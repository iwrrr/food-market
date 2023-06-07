package com.hwaryun.domain.mapper

import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.domain.model.User
import com.hwaryun.network.model.response.AuthDto

fun AuthDto.UserDto?.toViewParam(): User {
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