package ru.mvrlrd.feature_chat.navi

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mvrlrd.feature_chat.ChatScreen
import ru.mvrlrd.feature_chat_api.FeatureChatApi
import javax.inject.Inject

class FeatureChatImpl @Inject constructor(): FeatureChatApi {
    override val chatRoute: String
        get() = "chat"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier,
        action: () -> Unit
    ) {
        navGraphBuilder.composable("$chatRoute/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                ?: throw IllegalArgumentException("chat id is null")
            ChatScreen(
                modifier = modifier,
                chatId = id,
                onToggleTheme = action
            )
        }
    }

    //  composable("Chat/{id}") { backStackEntry ->
    ////                val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
    ////                ChatScreen(chatId = id) {
    ////                    onToggleTheme()
    ////                }


}