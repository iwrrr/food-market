package com.hwaryun.designsystem.components.molecules

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FavoriteButton(
    isChecked: Boolean,
    onClick: () -> Unit
) {
    IconToggleButton(
        modifier = Modifier
            .background(AsphaltTheme.colors.cool_gray_1cCp_100, AsphaltTheme.shapes.small)
            .size(36.dp),
        checked = isChecked,
        onCheckedChange = { onClick() }
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val icon = if (isChecked) {
            painterResource(id = R.drawable.ic_heart_bold)
        } else {
            painterResource(id = R.drawable.ic_heart)
        }

        val tint by transition.animateColor(
            label = "Tint"
        ) { isChecked ->
            if (isChecked) AsphaltTheme.colors.retail_red_500 else AsphaltTheme.colors.black_500
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        24.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        30.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        35.dp at 75 // ms
                        30.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 24.dp }

        Icon(
            painter = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    val (isChecked, setChecked) = remember { mutableStateOf(false) }
    FoodMarketTheme {
        FavoriteButton(
            isChecked = isChecked,
            onClick = { setChecked(!isChecked) }
        )
    }
}