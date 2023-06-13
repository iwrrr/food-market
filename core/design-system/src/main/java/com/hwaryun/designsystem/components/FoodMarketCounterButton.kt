package com.hwaryun.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodMarketCounterButton(
    modifier: Modifier = Modifier,
    value: Int,
    onDecrementClick: (Int) -> Unit,
    onIncrementClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(
            modifier = Modifier
                .size(26.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = { onDecrementClick(value) }
        ) {
            Icon(imageVector = Icons.Rounded.Remove, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$value",
            modifier = Modifier
                .widthIn(min = 24.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedIconButton(
            modifier = Modifier
                .size(26.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = { onIncrementClick(value) }
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
        }
    }
}