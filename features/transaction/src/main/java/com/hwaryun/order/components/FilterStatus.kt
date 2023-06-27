package com.hwaryun.order.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterStatus(
    filterAllSelected: Boolean = false,
    filterOnDeliverySelected: Boolean = false,
    filterDeliveredSelected: Boolean = false,
    filterCancelledSelected: Boolean = false,
    filterAll: () -> Unit,
    filterOnDelivery: () -> Unit,
    filterDelivered: () -> Unit,
    filterCancelled: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FilterChip(
            selected = filterAllSelected,
            onClick = { filterAll() },
            label = {
                AsphaltText(
                    text = "Semua",
                    style = AsphaltTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                )
            },
            shape = AsphaltTheme.shapes.small,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                labelColor = AsphaltTheme.colors.cool_gray_500,
                selectedContainerColor = AsphaltTheme.colors.gojek_green_50,
                selectedLabelColor = AsphaltTheme.colors.gojek_green_500,
            ),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = AsphaltTheme.colors.cool_gray_300,
                selectedBorderColor = AsphaltTheme.colors.gojek_green_500,
                borderWidth = 0.5.dp,
                selectedBorderWidth = 0.5.dp
            )
        )
        FilterChip(
            selected = filterOnDeliverySelected,
            onClick = { filterOnDelivery() },
            label = {
                AsphaltText(
                    text = "Dalam Proses",
                    style = AsphaltTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                )
            },
            shape = AsphaltTheme.shapes.small,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                labelColor = AsphaltTheme.colors.cool_gray_500,
                selectedContainerColor = AsphaltTheme.colors.gojek_green_50,
                selectedLabelColor = AsphaltTheme.colors.gojek_green_500,
            ),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = AsphaltTheme.colors.cool_gray_300,
                selectedBorderColor = AsphaltTheme.colors.gojek_green_500,
                borderWidth = 0.5.dp,
                selectedBorderWidth = 0.5.dp
            )
        )
        FilterChip(
            selected = filterDeliveredSelected,
            onClick = { filterDelivered() },
            label = {
                AsphaltText(
                    text = "Tiba di Tujuan",
                    style = AsphaltTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                )
            },
            shape = AsphaltTheme.shapes.small,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                labelColor = AsphaltTheme.colors.cool_gray_500,
                selectedContainerColor = AsphaltTheme.colors.gojek_green_50,
                selectedLabelColor = AsphaltTheme.colors.gojek_green_500,
            ),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = AsphaltTheme.colors.cool_gray_300,
                selectedBorderColor = AsphaltTheme.colors.gojek_green_500,
                borderWidth = 0.5.dp,
                selectedBorderWidth = 0.5.dp
            )
        )
        FilterChip(
            selected = filterCancelledSelected,
            onClick = { filterCancelled() },
            label = {
                AsphaltText(
                    text = "Dibatalkan",
                    style = AsphaltTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                )
            },
            shape = AsphaltTheme.shapes.small,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                labelColor = AsphaltTheme.colors.cool_gray_500,
                selectedContainerColor = AsphaltTheme.colors.gojek_green_50,
                selectedLabelColor = AsphaltTheme.colors.gojek_green_500,
            ),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = AsphaltTheme.colors.cool_gray_300,
                selectedBorderColor = AsphaltTheme.colors.gojek_green_500,
                borderWidth = 0.5.dp,
                selectedBorderWidth = 0.5.dp
            )
        )
    }
}