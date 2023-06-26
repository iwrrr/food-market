package com.hwaryun.foodmarket.navigation

import com.hwaryun.designsystem.R
import com.hwaryun.home.navigation.homeRoute
import com.hwaryun.order.navigation.transactionRoute
import com.hwaryun.profile.navigation.profileRoute
import com.hwaryun.search.navigation.searchRoute

enum class TopLevelDestination(
    val icon: Int,
    val label: String,
    val route: String
) {
    HOME(
        icon = R.drawable.ic_home,
        label = "Home",
        route = homeRoute,
    ),
    SEARCH(
        icon = R.drawable.ic_search,
        label = "Search",
        route = searchRoute,
    ),
    TRANSACTION(
        icon = R.drawable.ic_receipt_item,
        label = "Transaction",
        route = transactionRoute,
    ),
    PROFILE(
        icon = R.drawable.ic_profile_circle,
        label = "Profile",
        route = profileRoute,
    ),
}