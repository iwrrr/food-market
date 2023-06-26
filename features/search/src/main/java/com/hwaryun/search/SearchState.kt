package com.hwaryun.search

data class SearchState(
    val query: String = "",
    val focused: Boolean = false,
    val isLoading: Boolean = false
)
