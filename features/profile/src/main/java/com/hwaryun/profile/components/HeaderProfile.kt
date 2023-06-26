package com.hwaryun.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.common.ext.orDash
import com.hwaryun.designsystem.components.FoodMarketCircleImage
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.profile.ProfileUiState

@Composable
fun HeaderProfile(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
) {
    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .height(230.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FoodMarketCircleImage(
            image = uiState.user?.profilePhotoUrl,
            isLoading = uiState.isLoading,
            width = 110.dp,
            height = 110.dp,
            borderEnabled = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsphaltText(
            text = uiState.user?.name.orDash(),
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        AsphaltText(
            text = uiState.user?.email.orDash(),
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleSmallDemi,
            color = AsphaltTheme.colors.cool_gray_500,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderProfilePreview() {
    FoodMarketTheme {
        HeaderProfile(
            uiState = ProfileUiState(),
        )
    }
}