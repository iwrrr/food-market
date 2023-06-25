package com.hwaryun.designsystem.components

import android.content.res.Configuration
import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsphaltAppBar(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    clickDisablePeriod: Long = 1000L,
    showNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {},
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .height(100.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = AsphaltTheme.colors.pure_white_500),
            title = {
                Row {
                    Spacer(
                        modifier = Modifier.width(
                            if (showNavigateBack) 12.dp else 8.dp
                        )
                    )
                    AsphaltText(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        style = AsphaltTheme.typography.titleExtraLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            navigationIcon = {
                if (showNavigateBack) {
                    IconButton(
                        onClick = {
                            if (SystemClock.elapsedRealtime() - lastClickTime < clickDisablePeriod) {
                                return@IconButton
                            } else {
                                lastClickTime = SystemClock.elapsedRealtime()
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                }
            },
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TopBarPreview() {
    FoodMarketTheme {
        AsphaltAppBar(
            title = "Title",
            subtitle = "Subtitle",
        )
    }
}