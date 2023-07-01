package com.hwaryun.designsystem.components.molecules

import android.content.res.Configuration
import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
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
    modifier: Modifier = Modifier,
    clickDisablePeriod: Long = 1000L,
    showNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {},
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Box(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .height(60.dp)
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
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
        AsphaltText(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TopBarPreview() {
    FoodMarketTheme {
        AsphaltAppBar(
            title = "Title",
        )
    }
}