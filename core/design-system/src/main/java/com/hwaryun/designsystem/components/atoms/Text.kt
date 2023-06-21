package com.hwaryun.designsystem.components.atoms

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.LocalContentAlpha
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = AsphaltTheme.typography.bodyModerate,
    textAlign: TextAlign = TextAlign.Center
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }

    val mergedStyle = style.copy(color = textColor, textAlign = textAlign)

    BasicText(
        text = text,
        modifier = modifier,
        style = mergedStyle,
    )
}

@Composable
fun ProvideTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}

val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        Text(text = "asuuuuu")
    }
}