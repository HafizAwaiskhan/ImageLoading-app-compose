package com.example.jetpackapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController, // ✅ Pass NavController
    viewModel: HomeViewModel = hiltViewModel()
) {
    val images by viewModel.allImages.collectAsState()
    val favoriteImages by viewModel.favoriteImages.collectAsState()
    var isGridView by remember { mutableStateOf(true) } // Track view mode
    val gridState = rememberLazyGridState()
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        HeaderSection(
            "Home",
            isButtonEnabled = favoriteImages.isNotEmpty(),
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
                images = images,
                gridState = gridState,
                onLoadMore = { viewModel.loadNextPage() }, // Load more images on scroll
                isFavoriteScreen = false,
                onAddToFavorites = { image -> viewModel.saveImage(image) {} },
                onRemoveFromFavorites = { image -> viewModel.removeImage(image) }
            )
        } else {
            ImageList(
                images = images,
                lazyListState = lazyListState,
                onLoadMore = { viewModel.loadNextPage() }, // Load more images on scroll
                isFavoriteScreen = false,
                onAddToFavorites = { image -> viewModel.saveImage(image) {} },
                onRemoveFromFavorites = { image -> viewModel.removeImage(image) }
            )
        }
    }
}

