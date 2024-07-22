package ru.mvrlrd.main

import android.content.Context
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.canlioya.pullrefreshcomposesample.pullrefresh.PullToRefreshLayout
import com.canlioya.pullrefreshcomposesample.pullrefresh.rememberPullToRefreshState
import ru.mvrlrd.favorites.FavoritesScreen
import ru.mvrlrd.home.HomeScreen
import ru.mvrlrd.main.theme.CompanionTheme

private val viewModel: MainViewModel = MainViewModel()
@Composable
fun CompanionApp(onToggleTheme: () -> Unit, darkTheme: Boolean, context: Context) {

    CompanionTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()

            val pullToRefreshState = rememberPullToRefreshState(
                onTimeUpdated = { timeElapsed ->
                    viewModel.convertElapsedTimeIntoText(timeElapsed)
                },
            )

            val uiState by viewModel.uiState.collectAsState()

            Log.d("TAG","uiState received = $uiState")
            when (uiState) {
                is UiState.Initial->{
                    CompanionTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White,
                        ) {

                            PullToRefreshLayout(
                                modifier = Modifier.fillMaxSize(),
                                pullRefreshLayoutState = pullToRefreshState,
                                onRefresh = {
                                    viewModel.refresh()
                                },
                            ) {
                                HomeScreen()
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    // show error message
                }

                UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }

                }

                is UiState.Success -> {
                    FavoritesScreen((uiState as UiState.Success).list)
                }
            }


    }
}