package com.hwaryun.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.color
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
fun PlaceholderFoodItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(null)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.small
                    )
                    .width(100.dp)
                    .height(80.dp)
                    .clip(AsphaltTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .width(140.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = AsphaltTheme.shapes.small
                        ),
                    style = AsphaltTheme.typography.titleModerateBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .width(100.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = AsphaltTheme.shapes.small
                        ),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.cool_gray_500,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(thickness = 1.dp, color = AsphaltTheme.colors.cool_gray_1cCp_100)
                Spacer(modifier = Modifier.height(8.dp))
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .width(120.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = AsphaltTheme.shapes.small
                        ),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    color = AsphaltTheme.colors.sub_black_500,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        PlaceholderFoodItem()
    }
}