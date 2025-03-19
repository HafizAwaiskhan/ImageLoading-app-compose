package com.example.jetpackapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackapp.data.responsemodel.ImageResponse
import kotlinx.coroutines.launch

@Composable
fun ImageGrid(
    images: List<ImageResponse>,
    gridState: LazyGridState,
    onLoadMore: () -> Unit,
    isFavoriteScreen: Boolean,
    onAddToFavorites: (ImageResponse) -> Unit,
    onRemoveFromFavorites: (ImageResponse) -> Unit
) {
    val coroutineScope = rememberCoroutineScope() // ✅ Persistent coroutine scope

    // ✅ Collect scroll events
    LaunchedEffect(images) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: 0
                if (lastVisibleIndex >= images.size - 5 && images.isNotEmpty()) {
                    coroutineScope.launch {
                        onLoadMore() // ✅ Runs even when the list updates!
                    }
                }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState, // ✅ Attach scroll state
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images, key = { it.id }) { image -> // ✅ Ensure stable keys
            ImageItem(
                image = image,
                isFavoriteScreen = isFavoriteScreen,
                onAddToFavorites = onAddToFavorites,
                onRemoveFromFavorites = onRemoveFromFavorites
            )
        }
    }
}
