package com.hwaryun.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
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
fun HeaderHome(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .height(96.dp)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = "Your Location",
                    modifier = Modifier.fillMaxWidth(),
                    color = AsphaltTheme.colors.cool_gray_500,
                    style = AsphaltTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(12.dp))
                AsphaltText(
                    text = "Mars",
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.titleLarge,
                )
            }
            FilledIconButton(
                onClick = {},
                colors = IconButtonDefaults.filledIconButtonColors(AsphaltTheme.colors.cool_gray_1cCp_100)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bag_happy),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderHomePreview() {
    FoodMarketTheme {
        HeaderHome()
    }
}