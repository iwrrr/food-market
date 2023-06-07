package com.hwaryun.network.model.response

import com.google.gson.annotations.SerializedName

data class AuthDto(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    @SerializedName("user")
    val user: UserDto? = null
) {
    data class UserDto(
        @SerializedName("address")
        val address: String? = null,
        @SerializedName("city")
        val city: String? = null,
        @SerializedName("created_at")
        val createdAt: Long? = null,
        @SerializedName("current_team_id")
        val currentTeamId: Any? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("email_verified_at")
        val emailVerifiedAt: String? = null,
        @SerializedName("houseNumber")
        val houseNumber: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("phoneNumber")
        val phoneNumber: String? = null,
        @SerializedName("profile_photo_path")
        val profilePhotoPath: String? = null,
        @SerializedName("profile_photo_url")
        val profilePhotoUrl: String? = null,
        @SerializedName("roles")
        val roles: String? = null,
        @SerializedName("updated_at")
        val updatedAt: Long? = null
    )
}