package ru.mvrlrd.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.featureapi.FeatureApi
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    private val mainComponent by lazy {
        MainComponent.create(
            (application as AppWithFacade).getFacade())
    }
    @Inject
    lateinit var featureAPIes: Map<String,@JvmSuppressWildcards FeatureApi>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainComponent.inject(this)

        setContent {
            var isLightTheme by remember { mutableStateOf(false) }
            CompanionApp(
                onToggleTheme = {
                    isLightTheme = !isLightTheme
                },
                darkTheme = !isLightTheme,
                context = this,
            )
        }
    }
}
