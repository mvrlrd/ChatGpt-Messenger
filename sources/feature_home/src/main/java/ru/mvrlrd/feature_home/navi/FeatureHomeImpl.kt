package ru.mvrlrd.feature_home.navi

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_chat_api.FeatureChatApiProvider
import ru.mvrlrd.feature_home.HomeScreen
import ru.mvrlrd.feature_home_api.FeatureHomeApi
import javax.inject.Inject

class FeatureHomeImpl @Inject constructor(): FeatureHomeApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(homeRoute) {
            HomeScreen(modifier = modifier
            ) {

//                val chatFeature = (providersFacade as FeatureChatApiProvider).getFeatureChatApi()
                navController.navigate("chat/$it"){
//                    popUpTo(homeRoute) { inclusive = true } //????
                }
            }
        }
    }

    override val homeRoute: String
        get() = "home"
}