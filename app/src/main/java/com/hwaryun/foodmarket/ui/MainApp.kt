package com.hwaryun.foodmarket.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hwaryun.designsystem.ui.Primary
import com.hwaryun.foodmarket.navigation.MainAppNavHost
import com.hwaryun.foodmarket.navigation.TopLevelDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    mainAppState: MainAppState = rememberMainAppState()
) {
    Scaffold(
        bottomBar = {
            MainBottomBar(
                destinations = mainAppState.topLevelDestinations,
                onNavigateToDestination = mainAppState::navigateToTopLevelDestination,
                currentDestination = mainAppState.currentDestination,
                isVisible = mainAppState.shouldShowBottomBar
            )
        }
    ) {
        MainAppNavHost(
            mainAppState = mainAppState,
        )
    }
}

@Composable
fun MainBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            tonalElevation = 0.dp
        ) {
            destinations.forEach { topLevelDestination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(topLevelDestination)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(topLevelDestination) },
                    icon = {
                        val icon = if (selected) {
                            painterResource(id = topLevelDestination.selectedIcon)
                        } else {
                            painterResource(id = topLevelDestination.unselectedIcon)
                        }
                        Icon(icon, contentDescription = "")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Primary,
                        indicatorColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}

/**
 * Connect to the nested scroll system and listen to the scroll
 */
fun Modifier.bottomBarAnimatedScroll(
    height: Dp = 56.dp,
    offsetHeightPx: MutableState<Float>
): Modifier = composed {
    val bottomBarHeightPx = with(LocalDensity.current) {
        height.roundToPx().toFloat()
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = offsetHeightPx.value + delta
                offsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    this.nestedScroll(nestedScrollConnection)
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false