package com.hwaryun.designsystem.layout


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ChildLayout(
    val contentType: String = "",
    val isSticky: Boolean = false,
    val content: @Composable (item: Any?) -> Unit = {},
    val items: List<Any> = emptyList()
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalScrollLayout(
    modifier: Modifier = Modifier,
    state: LazyListState,
    vararg childLayouts: ChildLayout
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = state
    ) {
        childLayouts.forEach { child ->
            if (child.items.isEmpty()) {
                if (child.isSticky) {
                    stickyHeader {
                        child.content(null)
                    }
                } else {
                    loadItem(child)
                }
            } else {
                loadItems(child)
            }
        }
    }
}

/**
 * Use single item compose if no scroll or only horizontal scroll needed
 */
private fun LazyListScope.loadItem(childLayout: ChildLayout) {
    item(contentType = childLayout.contentType) {
        childLayout.content(null)
    }
}

/**
 * Use load multiple items to the lazy column when nested vertical scroll is needed
 */
private fun LazyListScope.loadItems(childLayout: ChildLayout) {
    items(items = childLayout.items) { item ->
        childLayout.content(item)
    }
}

/**
 * Compose items only if general item is successfully casted to defined class
 */
@Suppress("UNCHECKED_CAST")
@Composable
fun <T : Any> LoadItemAfterSafeCast(
    generalItem: Any?,
    composeWithSafeItem: @Composable (item: T) -> Unit
) {
    (generalItem as? T)?.let { safeItem ->
        composeWithSafeItem(safeItem)
    }
}