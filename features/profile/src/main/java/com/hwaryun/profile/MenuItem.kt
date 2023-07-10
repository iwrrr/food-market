package com.hwaryun.profile

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.basic.Surface
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes drawable: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.singleClick { onClick() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = drawable),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AsphaltTheme.colors.sub_black_500
                )
                Spacer(modifier = Modifier.width(8.dp))
                AsphaltText(
                    text = text,
                    modifier = Modifier.weight(1f),
                    style = AsphaltTheme.typography.titleSmallDemi,
                    color = AsphaltTheme.colors.sub_black_500
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = AsphaltTheme.colors.sub_black_500
                )
            }
            Divider(
                modifier = Modifier.offset(x = 48.dp),
                thickness = 0.5.dp,
                color = AsphaltTheme.colors.cool_gray_1cCp_500
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        MenuItem(
            text = "Menu",
            onClick = {},
            drawable = R.drawable.ic_order
        )
    }
}