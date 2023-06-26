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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.hwaryun.common.ext.convertUnixToDate
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.Transaction
import com.hwaryun.domain.model.User

@Composable
fun OrderItem(
    order: Transaction,
    onOrderItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .singleClick { onOrderItemClick(order.id) }
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(order.food.picturePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = order.food.name,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${order.quantity} items • IDR ${order.total.toNumberFormat()}",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary,
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
                Text(
                    text = order.updatedAt.convertUnixToDate("MMM d, HH:mm"),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
                if (order.status == "Cancelled") {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = order.status,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.error,
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderItemPreview() {
    FoodMarketTheme {
        OrderItem(
            order = Transaction(
                createdAt = 0L,
                deletedAt = 0L,
                foodId = 0,
                id = 0,
                paymentUrl = "",
                quantity = "",
                status = "",
                total = 0,
                updatedAt = 0L,
                userId = 0,
                food = Food(
                    description = "",
                    id = 0,
                    ingredients = "",
                    name = "",
                    picturePath = "",
                    price = 0,
                    rate = 0f,
                    types = ""
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
            onOrderItemClick = {}
        )
    }
}