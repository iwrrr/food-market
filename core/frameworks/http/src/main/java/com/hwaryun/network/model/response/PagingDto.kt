package com.hwaryun.network.model.response

import com.google.gson.annotations.SerializedName

data class PagingDto<T>(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("data")
    val results: List<T>,
    @SerializedName("first_page_url")
    val firstPageUrl: String? = null,
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("last_page_url")
    val lastPageUrl: String? = null,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("next_page_url")
    val nextPageUrl: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    @SerializedName("to")
    val to: Int? = null,
    @SerializedName("total")
    val total: Int? = null
) {

    data class Link(
        @SerializedName("active")
        val active: Boolean? = null,
        @SerializedName("label")
        val label: Any? = null,
        @SerializedName("url")
        val url: String? = null
    )
}