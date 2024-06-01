package ru.mvrlrd.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.mvrlrd.companion.ui.theme.CompanionTheme
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.home.HomeScreen


class MainActivity : ComponentActivity() {
    private val providersFacade: ProvidersFacade by lazy {
        (application as App).getFacade()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}







