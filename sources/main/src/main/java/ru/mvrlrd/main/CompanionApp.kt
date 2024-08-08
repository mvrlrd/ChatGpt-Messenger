package ru.mvrlrd.main

import android.content.Context
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.mvrlrd.feature_chat_api.FeatureChatApi
import ru.mvrlrd.feature_home_api.FeatureHomeApi
import ru.mvrlrd.featureapi.FeatureApi
import ru.mvrlrd.main.theme.JetHeroesTheme

@Composable
fun CompanionApp(
    onToggleTheme: () -> Unit,
    darkTheme: Boolean,
    context: Context,
) {
    val homeApi = (context as MainActivity).featureAPIes["home"] as FeatureHomeApi
    val chatApi = context.featureAPIes["chat"] as FeatureChatApi

    JetHeroesTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = homeApi.homeRoute,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + expandIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
        ) {
            register(
                navController= navController,
                modifier = Modifier,
                featureApi = chatApi,
                action = onToggleTheme
            )
            register(
                navController=navController,
                modifier = Modifier,
                featureApi = homeApi,
                action = {}
            )
        }
    }
}

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
    navController: NavController,
    modifier: Modifier = Modifier,
    action: ()->Unit
) {
    featureApi.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier,
        action=action
    )
}
