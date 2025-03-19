package com.example.jetpackapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.joinappstudioassignment.ui.home.AppNavGraph
import com.example.jetpackapp.ui.screens.AppTopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() // ✅ Create once & share

            Scaffold(
                topBar = { AppTopBar(navController) } // ✅ Set top bar
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    AppNavGraph(navController) // ✅ Navigation inside Scaffold
                }
            }
        }
    }
}