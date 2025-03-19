package com.example.jetpackapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HeaderSection(text: String, isButtonEnabled: Boolean, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Button(
            onClick = { navController.navigate(Screen.Favorites.route) },
            modifier = Modifier
                .alpha(if (isButtonEnabled) 1f else 0f)
                .height(if (isButtonEnabled) 48.dp else 0.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            enabled = isButtonEnabled
        ) {
            Text(text = "Favorites")
        }
    }
}