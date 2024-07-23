package ru.mvrlrd.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mvrlrd.favorites.FavoritesScreen
import ru.mvrlrd.home.HomeScreen
import ru.mvrlrd.main.theme.CompanionTheme

@Composable
fun CompanionApp(onToggleTheme: () -> Unit, darkTheme: Boolean, context: Context) {

    CompanionTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "Favs"
        ) {
            composable("Home") {
                HomeScreen(navController)
            }
            composable("Favs") {
                FavoritesScreen()
            }
        }
    }
}