package com.example.jetpackapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackapp.data.responsemodel.ImageResponse

@Composable
fun ImageItem(
    image: ImageResponse,
    isFavoriteScreen: Boolean,
    onAddToFavorites: (ImageResponse) -> Unit,
    onRemoveFromFavorites: (ImageResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.download_url)
                    .crossfade(true)
                    .memoryCacheKey(image.id.toString()) // ✅ Cache image with a unique key
                    .diskCacheKey(image.id.toString()) // ✅ Avoid refetching from network
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = image.author,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                    if (isFavoriteScreen) {
                        onRemoveFromFavorites(image)
                    } else {
                        onAddToFavorites(image)
                    }
                },
//                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isFavoriteScreen) "Remove from Fav" else "Add to Favorites",
                    textAlign = TextAlign.Center, // Centers text inside the button
//                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
