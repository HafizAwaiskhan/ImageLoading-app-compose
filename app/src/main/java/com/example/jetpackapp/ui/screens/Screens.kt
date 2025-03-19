package com.example.jetpackapp.ui.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Splash : Screen("splash")
}
