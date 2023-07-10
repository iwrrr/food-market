package com.hwaryun.designsystem.components.molecules

import android.content.res.Configuration
import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
fun AsphaltAppBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    clickDisablePeriod: Long = 1000L,
    showNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {},
    content: @Composable (() -> Unit)? = null
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Column {
        Row(
            modifier = modifier
                .background(AsphaltTheme.colors.pure_white_500)
                .padding(
                    start = if (showNavigateBack) 2.dp else 10.dp,
                    end = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Localized description"
                    )
                }
            }
            Spacer(
                modifier = Modifier.then(
                    if (showNavigateBack) {
                        Modifier.width(0.dp)
                    } else {
                        Modifier.width(6.dp)
                    }
                )
            )
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.titleModerateBold,
                )
                subtitle?.let {
                    AsphaltText(
                        text = subtitle,
                        modifier = Modifier.fillMaxWidth(),
                        style = AsphaltTheme.typography.captionSmallBook,
                        color = AsphaltTheme.colors.cool_gray_500,
                    )
                }
            }
        }
        content?.invoke()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        AsphaltAppBar(
            title = "Title",
            showNavigateBack = true
        )
    }
}