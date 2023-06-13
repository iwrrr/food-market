package com.hwaryun.designsystem.components

import android.os.SystemClock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodMarketCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    clickDisablePeriod: Long = 1000L,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Card(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime < clickDisablePeriod) {
                return@Card
            } else {
                lastClickTime = SystemClock.elapsedRealtime()
                onClick()
            }
        },
        content = content
    )
}