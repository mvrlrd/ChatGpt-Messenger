package ru.mvrlrd.feature_home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mvrlrd.feature_home.HomeScreen
import ru.mvrlrd.feature_home_api.FeatureHomeApi
import javax.inject.Inject

class FeatureHomeImpl @Inject constructor() : FeatureHomeApi {
    override val homeRoute: String
        get() = "home"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier,
        action: () -> Unit
    ) {
        navGraphBuilder.composable(homeRoute) {
            HomeScreen(
                modifier = modifier,
                openSettings = {navController.navigate("settings")}
            ) {
                navController.navigate("chat/$it") {
//                    popUpTo(homeRoute) { inclusive = true } //????
                }
            }
        }
    }
}