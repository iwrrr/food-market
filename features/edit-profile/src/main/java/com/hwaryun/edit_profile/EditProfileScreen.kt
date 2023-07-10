package com.hwaryun.edit_profile

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.hwaryun.designsystem.components.molecules.AsphaltAppBar
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.edit_profile.utils.uriToFile
import java.io.File

@Composable
internal fun EditProfileRoute(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EditProfileScreen(
        state = state,
        popBackStack = popBackStack,
        onShowSnackbar = onShowSnackbar,
        updateNameState = viewModel::updateNameState,
        updateEmailState = viewModel::updateEmailState,
        updatePhoneNumberState = viewModel::updatePhoneNumberState,
        onChangePhoto = viewModel::changePhoto,
        onSaveClick = viewModel::updateProfile,
        resetErrorState = viewModel::resetErrorState
    )
}

@Composable
fun EditProfileScreen(
    state: EditProfileState,
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onChangePhoto: (photo: File?) -> Unit,
    updateNameState: (String) -> Unit,
    updateEmailState: (String) -> Unit,
    updatePhoneNumberState: (String) -> Unit,
    onSaveClick: (name: String?, phoneNumber: String?, email: String?) -> Unit,
    resetErrorState: () -> Unit,
) {
    var shouldShowTrailingIcon by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
            imageUri?.let { uri ->
                val file = uriToFile(uri, context)
                onChangePhoto(file)
            }
        }

    LaunchedEffect(state) {
        if (state.updatePhoto != null) {
            focusManager.clearFocus()
            onShowSnackbar("Berhasil update foto profil", null)
        }

        if (state.updateProfile != null) {
            focusManager.clearFocus()
            onShowSnackbar("Berhasil update profil", null)
        }

        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.pure_white_500,
        topBar = {
            AsphaltAppBar(
                title = stringResource(id = R.string.title_edit_profile),
                showNavigateBack = true,
                onNavigateBack = popBackStack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                ),
        ) {
            AsphaltText(
                text = stringResource(id = R.string.title_profile_photo),
                style = AsphaltTheme.typography.captionModerateDemi
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.singleClick { galleryLauncher.launch("image/*") },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUri ?: state.user?.profilePhotoUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_photo_placeholder),
                        error = painterResource(R.drawable.ic_photo_placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .placeholder(
                                visible = false,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = PlaceholderDefaults.color(),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AsphaltText(
                        text = stringResource(id = R.string.change),
                        style = AsphaltTheme.typography.captionSmallBook
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                AsphaltText(
                    text = stringResource(id = R.string.desc_profile_photo),
                    modifier = Modifier.offset(y = (-14).dp),
                    style = AsphaltTheme.typography.captionSmallBook
                )
            }
            Spacer(modifier = Modifier.height(42.dp))
            AsphaltInputGroup(
                value = state.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                onValueChange = { updateNameState(it) },
                showLabel = true,
                required = true,
                label = stringResource(id = R.string.label_full_name),
                placeholder = stringResource(id = R.string.placeholder_full_name),
                singleLine = true,
                isError = state.isNameError,
                errorMsg = if (state.isNameError) stringResource(id = state.errorNameMsg) else "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    if (state.name.isNotEmpty() && shouldShowTrailingIcon) {
                        IconButton(
                            modifier = Modifier.size(20.dp),
                            onClick = { updateNameState("") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            AsphaltInputGroup(
                value = state.phoneNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                onValueChange = { updatePhoneNumberState(it) },
                showLabel = true,
                required = true,
                label = stringResource(id = R.string.label_phone_number),
                placeholder = stringResource(id = R.string.placeholder_phone_number),
                singleLine = true,
                isError = state.isPhoneNumberError,
                errorMsg = if (state.isPhoneNumberError) stringResource(id = state.errorPhoneNumberMsg) else "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    if (state.phoneNumber.isNotEmpty() && shouldShowTrailingIcon) {
                        IconButton(
                            modifier = Modifier.size(20.dp),
                            onClick = { updatePhoneNumberState("") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            AsphaltInputGroup(
                value = state.email,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                onValueChange = { updateEmailState(it) },
                showLabel = true,
                required = true,
                label = stringResource(id = R.string.label_email),
                placeholder = stringResource(id = R.string.placeholder_email),
                singleLine = true,
                isError = state.isEmailError,
                errorMsg = if (state.isEmailError) stringResource(id = state.errorEmailMsg) else "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    if (state.email.isNotEmpty() && shouldShowTrailingIcon) {
                        IconButton(
                            modifier = Modifier.size(20.dp),
                            onClick = { updateEmailState("") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            AsphaltButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                isLoading = state.isLoading,
                onClick = { onSaveClick(state.name, state.phoneNumber, state.email) },
            ) {
                AsphaltText(text = stringResource(id = R.string.btn_save))
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        EditProfileScreen(
            state = EditProfileState(),
            popBackStack = {},
            onShowSnackbar = { _, _ -> false },
            onChangePhoto = {},
            updateNameState = {},
            updateEmailState = {},
            updatePhoneNumberState = {},
            onSaveClick = { _, _, _ -> },
            resetErrorState = {},
        )
    }
}