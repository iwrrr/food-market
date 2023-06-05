package com.hwaryun.foodmarket.navigation

import com.hwaryun.designsystem.R
import com.hwaryun.home.navigation.homeRoute
import com.hwaryun.order.navigation.orderRoute
import com.hwaryun.profile.navigation.profileRoute

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: String,
    val route: String
) {
    HOME(
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home_normal,
        iconTextId = "Home",
        route = homeRoute,
    ),
    ORDER(
        selectedIcon = R.drawable.ic_order,
        unselectedIcon = R.drawable.ic_order_normal,
        iconTextId = "Order",
        route = orderRoute,
    ),
    PROFILE(
        selectedIcon = R.drawable.ic_profile,
        unselectedIcon = R.drawable.ic_profile_normal,
        iconTextId = "Profile",
        route = profileRoute,
    ),
}