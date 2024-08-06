package ru.mvrlrd.feature_chat.navi

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.mvrlrd.feature_chat.ChatScreen
import ru.mvrlrd.feature_chat_api.ChatFeatureApi
import javax.inject.Inject

class ChatFeatureImpl @Inject constructor(private val onToggleTheme: ()-> Unit): ChatFeatureApi {
    override val chatRoute: String
        get() = "chat/{id}"
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(chatRoute) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                ?: throw IllegalArgumentException("chat id is null")
            ChatScreen(
                modifier = modifier,
                chatId = id,
                onToggleTheme = onToggleTheme
            )
        }
    }

    //  composable("Chat/{id}") { backStackEntry ->
    ////                val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
    ////                ChatScreen(chatId = id) {
    ////                    onToggleTheme()
    ////                }


}