package com.example.joinappstudioassignment.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackapp.ui.screens.FavoriteScreen
import com.example.jetpackapp.ui.screens.HomeScreen
import com.example.jetpackapp.ui.screens.Screen
import com.example.jetpackapp.ui.screens.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Favorites.route) { FavoriteScreen(navController) }
    }
}