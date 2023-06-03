package com.hwaryun.foodmarket.navigation

import com.hwaryun.designsystem.R

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: String,
    val titleTextId: String
) {
    HOME(
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home_normal,
        iconTextId = "Home",
        titleTextId = "Home",
    ),
    ORDER(
        selectedIcon = R.drawable.ic_order,
        unselectedIcon = R.drawable.ic_order_normal,
        iconTextId = "Order",
        titleTextId = "Order",
    ),
    PROFILE(
        selectedIcon = R.drawable.ic_profile,
        unselectedIcon = R.drawable.ic_profile_normal,
        iconTextId = "Profile",
        titleTextId = "Profile",
    ),
}