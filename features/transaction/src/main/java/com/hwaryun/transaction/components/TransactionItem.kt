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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.common.ext.convertUnixToDate
import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.components.organisms.FoodImage
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.Transaction
import com.hwaryun.domain.model.User

@Composable
fun TransactionItem(
    transaction: Transaction?,
    onTransactionClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .singleClick { onTransactionClick(transaction?.id.orZero()) }
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            FoodImage(
                url = transaction?.food?.picturePath,
                rate = transaction?.food?.rate.toString()
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Top
            ) {
                AsphaltText(
                    text = transaction?.food?.name.orDash(),
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.titleModerateBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    AsphaltText(
                        text = transaction?.updatedAt?.convertUnixToDate("dd MMM yyyy, HH:mm")
                            .orDash(),
                        style = AsphaltTheme.typography.captionSmallBook,
                        color = AsphaltTheme.colors.cool_gray_500,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AsphaltText(
                        text = "•",
                        style = AsphaltTheme.typography.captionModerateDemi,
                        color = AsphaltTheme.colors.cool_gray_500,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AsphaltText(
                        text = transaction?.status.orDash(),
                        style = AsphaltTheme.typography.captionSmallDemi,
                        color = AsphaltTheme.colors.cool_gray_500,
                    )
                }
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
                    text = transaction?.total.toNumberFormat(),
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.bodyModerate.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                AsphaltText(
                    text = "1 menu",
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    color = AsphaltTheme.colors.cool_gray_500
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                AsphaltButton(
                    modifier = Modifier.scale(0.9f),
                    type = ButtonType.Outline,
                    onClick = {}
                ) {
                    AsphaltText(text = "Pesan lagi")
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        TransactionItem(
            transaction = Transaction(
                createdAt = 0L,
                deletedAt = 0L,
                foodId = 0,
                id = 0,
                paymentUrl = "",
                quantity = "",
                status = "Selesai",
                total = 0,
                updatedAt = 0L,
                userId = 0,
                food = Food(
                    description = "",
                    id = 0,
                    ingredients = "",
                    name = "Kopi Janji Jiwa",
                    picturePath = "",
                    price = 0,
                    rate = 0f,
                    types = "",
                    deliveryTime = 10
                ),
                user = User(
                    address = "",
                    city = "",
                    email = "",
                    houseNumber = "",
                    id = 0,
                    name = "",
                    phoneNumber = "",
                    profilePhotoPath = "",
                    profilePhotoUrl = ""
                )
            ),
            onTransactionClick = {}
        )
    }
}