package com.hwaryun.designsystem.components.organisms

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.shadow

@Composable
fun FoodImage(
    url: String?,
    rate: String?,
) {
    Box(
        modifier = Modifier.padding(bottom = 14.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            error = painterResource(R.drawable.ic_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .shadow(
                    color = AsphaltTheme.colors.cool_gray_1cCp_500,
                    blurRadius = 6.dp,
                    offsetY = 0.dp
                )
                .width(100.dp)
                .height(80.dp)
                .clip(AsphaltTheme.shapes.small)
        )
        Row(
            modifier = Modifier
                .offset(y = 12.dp)
                .shadow(color = AsphaltTheme.colors.cool_gray_1cCp_500)
                .width(60.dp)
                .height(24.dp)
                .clip(AsphaltTheme.shapes.small)
                .background(AsphaltTheme.colors.pure_white_500),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = AsphaltTheme.colors.odd_job_orange_500
            )
            Spacer(modifier = Modifier.width(4.dp))
            AsphaltText(
                text = "$rate",
                style = AsphaltTheme.typography.captionModerateDemi.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        FoodImage(url = null, rate = "5.0")
    }
}