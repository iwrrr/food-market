package com.hwaryun.foodmarket.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hwaryun.datasource.util.NetworkMonitor
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltBottomNavigation
import com.hwaryun.designsystem.components.atoms.AsphaltNavigationItem
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor
import com.hwaryun.foodmarket.navigation.MainAppNavHost
import com.hwaryun.foodmarket.navigation.TopLevelDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(
    networkMonitor: NetworkMonitor,
    mainAppState: MainAppState = rememberMainAppState(networkMonitor = networkMonitor),
    startDestination: String
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var isReconnected by remember { mutableStateOf(false) }

    val isOffline by mainAppState.isOffline.collectAsStateWithLifecycle()

    val connectionMessage =
        if (isOffline) stringResource(R.string.not_connected_message) else stringResource(R.string.connected_message)
    val connectionIcon =
        if (isOffline) painterResource(id = R.drawable.ic_wifi_broken) else painterResource(id = R.drawable.ic_wifi)

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = connectionMessage,
                duration = SnackbarDuration.Short,
            )
            return@LaunchedEffect
        }

        if (isReconnected) {
            snackbarHostState.showSnackbar(
                message = connectionMessage,
                duration = SnackbarDuration.Short,
            )
        }

        isReconnected = true
    }

    Scaffold(
        containerColor = AsphaltTheme.colors.pure_white_500,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    containerColor = AsphaltTheme.colors.cool_gray_1cCp_50,
                    contentColor = AsphaltTheme.colors.sub_black_500
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (data.visuals.message) {
                            connectionMessage -> {
                                Icon(painter = connectionIcon, contentDescription = null)
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            else -> {}
                        }
                        AsphaltText(
                            text = data.visuals.message,
                            style = AsphaltTheme.typography.captionModerateBook
                        )
                    }
                }
            }
        },
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
            startDestination = startDestination,
            onShowSnackbar = { message, actionLabel ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short,
                ) == SnackbarResult.ActionPerformed
            }
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
        AsphaltBottomNavigation {
            destinations.forEach { topLevelDestination ->
                val selected =
                    currentDestination.isTopLevelDestinationInHierarchy(topLevelDestination)
                AsphaltNavigationItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(topLevelDestination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = topLevelDestination.icon),
                            contentDescription = topLevelDestination.label,
                            tint = LocalContentColor.current
                        )
                    },
                    label = {
                        AsphaltText(text = topLevelDestination.label)
                    }
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