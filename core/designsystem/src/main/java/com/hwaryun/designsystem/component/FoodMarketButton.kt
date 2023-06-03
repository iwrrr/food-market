package com.hwaryun.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.Black
import com.hwaryun.designsystem.ui.DarkerGrey
import com.hwaryun.designsystem.ui.DarkerYellow
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.Grey
import com.hwaryun.designsystem.ui.Yellow

@Composable
fun FoodMarketButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.Primary,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val textColor: Color
    val color: Color

    when (type) {
        ButtonType.Primary -> {
            textColor = Black
            color = ButtonType.Primary.colorDefault
        }

        ButtonType.Secondary -> {
            textColor = Color.White
            color = ButtonType.Secondary.colorDefault
        }
    }

    Button(
        modifier = modifier.height(48.dp),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(color),
        onClick = { onClick() },
    ) {
        Text(text = text, color = textColor)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PowerHumanButtonPreview() {
    FoodMarketTheme {
        FoodMarketButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Button",
            onClick = {}
        )
    }
}

sealed class ButtonType(
    val colorDefault: Color,
    val colorPressed: Color,
) {
    object Primary : ButtonType(
        colorDefault = Yellow,
        colorPressed = DarkerYellow
    )

    object Secondary : ButtonType(
        colorDefault = Grey,
        colorPressed = DarkerGrey
    )
}