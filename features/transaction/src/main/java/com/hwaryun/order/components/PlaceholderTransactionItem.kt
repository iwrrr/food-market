package com.hwaryun.order.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
fun PlaceholderTransactionItem() {
    Column(
        modifier = Modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                    .size(60.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth(2f / 3)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.titleModerateDemi,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth(1f / 2)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.cool_gray_500,
                    maxLines = 1
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.cool_gray_500,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(2.dp))
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth(3f / 4)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.retail_red_500,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        PlaceholderTransactionItem()
    }
}