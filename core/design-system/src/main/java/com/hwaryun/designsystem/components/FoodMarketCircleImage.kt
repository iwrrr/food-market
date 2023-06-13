package com.hwaryun.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.color
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
fun FoodMarketCircleImage(
    image: String? = null,
    isLoading: Boolean = false,
    width: Dp,
    height: Dp,
    borderEnabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .then(
                if (borderEnabled) {
                    Modifier
                        .size(width, height)
                        .dashedBorder(1.dp, Color(0xFF8D92A3), width)
                        .padding(10.dp)
                } else {
                    Modifier.padding(0.dp)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_photo_placeholder),
            error = painterResource(R.drawable.ic_photo_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(width)
                .height(height)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable { onClick() }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodMarketCircleImagePreview() {
    FoodMarketTheme {
        FoodMarketCircleImage(width = 60.dp, height = 60.dp, borderEnabled = true)
    }
}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f)
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)