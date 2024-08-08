package ru.mvrlrd.chat_settings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mvrlrd.chat_settings.SettingsScreen
import ru.mvrlrd.settings_api.FeatureSettingsApi
import javax.inject.Inject

class FeatureSettingsImpl @Inject constructor(): FeatureSettingsApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier,
        action: () -> Unit
    ) {

        navGraphBuilder.composable("${settingsRoute}/{id}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                ?: throw IllegalArgumentException("chat id is null")
            SettingsScreen(
                chatId= chatId
            ) {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true } //????
                }
            }
        }
    }

    override val settingsRoute: String
        get() = "settings"
}