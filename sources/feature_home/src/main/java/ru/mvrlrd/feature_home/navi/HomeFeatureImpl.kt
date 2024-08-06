package ru.mvrlrd.feature_home.navi

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_chat_api.ChatFeatureApi
import ru.mvrlrd.feature_home.HomeScreen
import ru.mvrlrd.feature_home_api.HomeFeatureApi
import javax.inject.Inject

class HomeFeatureImpl @Inject constructor(private  val providersFacade: ProvidersFacade): HomeFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(homeRoute) {
            HomeScreen(modifier = modifier,
                    providersFacade = providersFacade
            ) {
                //  val homeFeature = DependencyProvider.homeFeature()
                navController.navigate("chat/$it"){
//                    popUpTo(homeRoute) { inclusive = true } //????
                }
            }
        }
    }

    override val homeRoute: String
        get() = "home"
}