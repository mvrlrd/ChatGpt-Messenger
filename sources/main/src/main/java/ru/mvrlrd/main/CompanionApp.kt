package ru.mvrlrd.main

import android.content.Context
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_home.HomeScreen
import ru.mvrlrd.feature_chat.ChatScreen
import ru.mvrlrd.feature_chat.navi.ChatFeatureImpl
import ru.mvrlrd.feature_home.navi.HomeFeatureImpl
import ru.mvrlrd.featureapi.FeatureApi
import ru.mvrlrd.main.theme.JetHeroesTheme

@Composable
fun CompanionApp(
    onToggleTheme: () -> Unit,
    darkTheme: Boolean,
    context: Context,
    providersFacade: ProvidersFacade
) {
    val chatFeatureImpl = remember{
        ChatFeatureImpl(onToggleTheme)
    }
    val homeFeatureImpl = remember{
        HomeFeatureImpl(providersFacade)
    }

    JetHeroesTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = homeFeatureImpl.homeRoute,
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
                featureApi = chatFeatureImpl
            )
            register(
                navController=navController,
                modifier = Modifier,
                featureApi = homeFeatureImpl
            )
        }
    }
}

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    featureApi.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier
    )
}
