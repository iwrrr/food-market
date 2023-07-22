package com.hwaryun.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.color
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.components.molecules.AsphaltAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick

@Composable
internal fun ProfileRoute(
    navigateToTransaction: () -> Unit,
    navigateToEditProfile: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    ProfileScreen(
        state = profileState,
        navigateToTransaction = navigateToTransaction,
        navigateToEditProfile = navigateToEditProfile,
        onShowSnackbar = onShowSnackbar,
        refresh = viewModel::refresh,
        onLogoutClick = viewModel::logout,
        resetErrorState = viewModel::resetErrorState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    navigateToTransaction: () -> Unit,
    navigateToEditProfile: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    refresh: () -> Unit,
    onLogoutClick: () -> Unit,
    resetErrorState: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = refresh)

    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    BackHandler(state.isLoading) {
        return@BackHandler
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.pure_white_500,
        topBar = { AsphaltAppBar(title = stringResource(id = R.string.title_profile)) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = innerPadding.calculateTopPadding() + 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 16.dp
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.user?.profilePhotoUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_photo_placeholder),
                        error = painterResource(R.drawable.ic_photo_placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .placeholder(
                                visible = false,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = PlaceholderDefaults.color(),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(3f)
                    ) {
                        AsphaltText(
                            text = "${state.user?.name}",
                            modifier = Modifier.fillMaxWidth(),
                            style = AsphaltTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        AsphaltText(
                            text = "${state.user?.email}",
                            modifier = Modifier.fillMaxWidth(),
                            style = AsphaltTheme.typography.bodyModerate,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        AsphaltText(
                            text = "${state.user?.phoneNumber}",
                            modifier = Modifier.fillMaxWidth(),
                            style = AsphaltTheme.typography.bodyModerate,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier.singleClick { navigateToEditProfile() },
                        tint = AsphaltTheme.colors.sub_black_700
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                MenuItem(
                    text = "Pesanan",
                    drawable = R.drawable.ic_order,
                    onClick = { navigateToTransaction() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                        )
                ) {
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Outline,
                        enabled = !state.isLoading,
                        isLoading = state.isLoading,
                        onClick = { onLogoutClick() },
                    ) {
                        AsphaltText(text = "Logout")
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = false,
                state = pullRefreshState,
                contentColor = AsphaltTheme.colors.gojek_green_500
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        ProfileScreen(
            state = ProfileState(),
            navigateToTransaction = {},
            navigateToEditProfile = {},
            onShowSnackbar = { _, _ -> false },
            refresh = {},
            onLogoutClick = {},
            resetErrorState = {},
        )
    }
}