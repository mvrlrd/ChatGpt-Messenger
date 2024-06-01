package ru.mvrlrd.companion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.canlioya.pullrefreshcomposesample.pullrefresh.PullToRefreshLayout
import com.canlioya.pullrefreshcomposesample.pullrefresh.rememberPullToRefreshState
import ru.mvrlrd.companion.ui.theme.CompanionTheme
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.home.HomeScreen


class MainActivity : ComponentActivity() {
    private val providersFacade: ProvidersFacade by lazy {
        (application as App).getFacade()
    }
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val pullToRefreshState = rememberPullToRefreshState(
                onTimeUpdated = { timeElapsed ->
                    viewModel.convertElapsedTimeIntoText(timeElapsed)
                },
            )

            val uiState by viewModel.uiState.collectAsState()


            when (uiState) {
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
                is UiState.Home -> {
                    HomeScreen()
                }

                is UiState.Success -> {
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
                                    (uiState as? UiState.Success)?.data?.let { colors ->
                                        LazyColumn(
                                            modifier = Modifier.fillMaxSize(),
                                            contentPadding = PaddingValues(15.dp),
                                            verticalArrangement = Arrangement.spacedBy(15.dp),
                                            state = viewModel.scrollState,
                                        ) {
                                            items(items = colors, key = { it.toArgb() }) { color ->
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(80.dp)
                                                        .background(color)
                                                        .animateItemPlacement(),
                                                )
                                            }
                                        }

                                }
                            }

                        }
                    }
                }
            }


//            CompanionTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    HomeScreen()


        }

    }
}







