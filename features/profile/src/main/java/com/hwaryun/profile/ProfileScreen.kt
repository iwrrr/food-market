package com.hwaryun.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
internal fun ProfileRoute(onButtonClick: () -> Unit) {
    ProfileScreen(onButtonClick = onButtonClick)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(onButtonClick: () -> Unit) {
    Button(onClick = {
        onButtonClick()
        Log.d("WatchListScreen", "Event click listener")
    }) {
        Text("Navigate to detail")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    items: List<String>, onSave: (name: String) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier) {
        var nameMyModel by remember { mutableStateOf("Compose") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(value = nameMyModel, onValueChange = { nameMyModel = it })

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameMyModel) }) {
                Text("Save")
            }
        }
        items.forEach {
            Text("Saved item: $it")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        ProfileScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    FoodMarketTheme {
        ProfileScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}