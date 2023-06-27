package com.hwaryun.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.color
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.toNumberFormat

@Composable
fun PaymentSummary(
    totalFoodPrice: Int = 0,
    shippingCost: Int = 10000,
    serviceFee: Int = 5000,
    totalPrice: Int = 0,
    isLoading: Boolean = false
) {
    Column {
        AsphaltText(
            text = "Ringkasan pembayaran",
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleSmallBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Harga",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = totalFoodPrice.toNumberFormat(),
                modifier = Modifier
                    .weight(1f)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Ongkir",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = shippingCost.toNumberFormat(),
                modifier = Modifier
                    .weight(1f)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Biaya layanan & lainnya",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = serviceFee.toNumberFormat(),
                modifier = Modifier
                    .weight(1f)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = AsphaltTheme.colors.cool_gray_1cCp_100)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Total pembayaran",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            AsphaltText(
                text = totalPrice.toNumberFormat(),
                modifier = Modifier
                    .weight(1f)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.End
            )
        }
    }
}