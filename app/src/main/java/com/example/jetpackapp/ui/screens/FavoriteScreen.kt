package com.example.jetpackapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackapp.viewmodels.HomeViewModel

@Composable
fun FavoriteScreen(
    navController: NavController, // ✅ Pass NavController
    viewModel: HomeViewModel = hiltViewModel()
) {
    val favoriteImages by viewModel.favoriteImages.collectAsState()
    var isGridView by remember { mutableStateOf(true) } // Track view mode
    val gridState = rememberLazyGridState()
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        HeaderSection(
            "Favorites",
            false,
            navController = navController
        )

        // Toggle Button
        Button(
            onClick = { isGridView = !isGridView },
//            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isGridView) "List View" else "Grid View")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isGridView) {
            ImageGrid(
                images = favoriteImages,
                gridState = gridState,
                onLoadMore = { viewModel.loadNextPage() }, // Load more images on scroll
                isFavoriteScreen = true,
                onAddToFavorites = { image -> viewModel.saveImage(image) {} },
                onRemoveFromFavorites = { image -> viewModel.removeImage(image) }
            )
        } else {
            ImageList(
                images = favoriteImages,
                lazyListState = lazyListState,
                onLoadMore = { viewModel.loadNextPage() }, // Load more images on scroll
                isFavoriteScreen = true,
                onAddToFavorites = { image -> viewModel.saveImage(image) {} },
                onRemoveFromFavorites = { image -> viewModel.removeImage(image) }
            )
        }
    }
}