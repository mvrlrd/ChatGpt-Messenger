package ru.mvrlrd.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            Text("hello")
//        }
//
//    }
//}


//    private val providersFacade: ProvidersFacade by lazy {
//        (application as App).getFacade()
//    }
//    private val viewModel: MainViewModel by lazy {
//        val dao  = providersFacade.answersDao()
//        Log.d("TAG", "byLAzy = $dao")
//        MainViewModel(GetFavoritesAnswersUseCaseImpl(dao))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLightTheme by remember { mutableStateOf(true) }
            CompanionApp(
                onToggleTheme = {
                    isLightTheme = !isLightTheme
                },
                darkTheme = !isLightTheme,
                context = this
            )
        }
    }
}
