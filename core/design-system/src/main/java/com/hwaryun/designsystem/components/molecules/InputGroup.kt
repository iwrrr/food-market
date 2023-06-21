package com.hwaryun.designsystem.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.components.atoms.Input
import com.hwaryun.designsystem.components.atoms.Text
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
fun InputGroup(
    label: String,
    onInputChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    input: String = "",
    sublabel: String? = null,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            modifier = Modifier.fillMaxWidth(),
            value = input,
            onValueChange = onInputChange,
            shape = shape,
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (sublabel != null) {
            Text(
                text = sublabel,
                style = AsphaltTheme.typography.captionModerateDemi.copy(
                    fontWeight = FontWeight.Normal,
                    color = AsphaltTheme.colors.cool_gray_500,
                )
            )
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        InputGroup(label = "", onInputChange = {})
    }
}