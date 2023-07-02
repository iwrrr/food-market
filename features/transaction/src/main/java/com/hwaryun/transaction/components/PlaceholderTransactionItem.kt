package com.hwaryun.transaction.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
fun PlaceholderTransactionItem() {
    Column(
        modifier = Modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
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
                    .weight(2f),
                verticalArrangement = Arrangement.Top
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
                    style = AsphaltTheme.typography.captionSmallBook,
                    color = AsphaltTheme.colors.cool_gray_500,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(thickness = 1.dp, color = AsphaltTheme.colors.cool_gray_1cCp_100)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
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
                    style = AsphaltTheme.typography.bodyModerate.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                AsphaltText(
                    text = "",
                    modifier = Modifier
                        .width(50.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = AsphaltTheme.shapes.small
                        ),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    color = AsphaltTheme.colors.cool_gray_500
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                AsphaltButton(
                    modifier = Modifier
                        .scale(0.9f)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = AsphaltTheme.shapes.medium
                        ),
                    type = ButtonType.Outline,
                    onClick = {}
                ) {
                    AsphaltText(text = "Pesan lagi")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        PlaceholderTransactionItem()
    }
}