package ru.mvrlrd.main

import android.content.Context
import android.content.res.Resources.Theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.favorites.FavoritesScreen
//import ru.mvrlrd.home.HomeScreen
import ru.mvrlrd.home.HomeScreen
import ru.mvrlrd.main.theme.AppColors
import ru.mvrlrd.main.theme.AppTheme
import ru.mvrlrd.main.theme.JetHeroesTheme

@Composable
fun CompanionApp(
    onToggleTheme: () -> Unit,
    darkTheme: Boolean,
    context: Context,
    providersFacade: ProvidersFacade
) {

    JetHeroesTheme(darkTheme = darkTheme) {

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "Favs"
        ) {
            composable("Favs") {
                FavoritesScreen(providersFacade = providersFacade) {
                    navController.navigate("Home/$it")
                    //разобраться с навигацией!
                    //улучшить анимацию ресайклеров
                    //добавить экран создания чата
                    // убирать фаб при скроле
                    //поменять цвет баббл от ИИ
                    //добавить отбивку дат в чат
                }
            }
            composable("Home/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
                HomeScreen(chatId = id) {
                    onToggleTheme()
                }
            }
        }
    }
}