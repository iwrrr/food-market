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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.hwaryun.designsystem.ui.FoodMarketTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodMarketTopAppBar(
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
            .background(MaterialTheme.colorScheme.surface)
            .height(100.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = {
                Row {
                    Spacer(
                        modifier = Modifier.width(
                            if (showNavigateBack) 12.dp else 8.dp
                        )
                    )
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TopBarPreview() {
    FoodMarketTheme {
        FoodMarketTopAppBar(
            title = "Title",
            subtitle = "Subtitle",
        )
    }
}