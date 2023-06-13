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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.utils.singleClick

@Composable
fun PlaceholderVerticalItem() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .singleClick { }
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
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
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    value = 0f,
                    config = RatingBarConfig()
                        .isIndicator(true)
                        .size(16.dp),
                    onValueChange = {},
                    onRatingChanged = {}
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "",
                    modifier = Modifier
                        .width(24.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        PlaceholderVerticalItem()
    }
}