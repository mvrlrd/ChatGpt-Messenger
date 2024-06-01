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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

            Log.d("TAG","uiState received = $uiState")
            when (uiState) {
                is UiState.Initial->{
                    Log.d("TAG","___ Initial")
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
                                MyScrollableScreen()
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    // show error message
                }

                UiState.Loading -> {
                    Log.d("TAG","___ Loading")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }

                }

                is UiState.Success -> {
                    Log.d("TAG","___ Success")
                    HomeScreen()
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

@Composable
fun MyScrollableScreen() {
    // Create a scroll state
    val scrollState = rememberScrollState()

    // Use Column with verticalScroll to create a scrollable background
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen
            .background(Color.Cyan) // Set the background color
            .verticalScroll(scrollState), // Enable vertical scrolling
        horizontalAlignment = Alignment.CenterHorizontally, // Center the content horizontally
        verticalArrangement = Arrangement.Center // Center the content vertically
    ) {
        Text(
            text = "Hello, Compose!",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp) // Add padding to the text
        )

        // Add additional items to demonstrate scrolling
        for (i in 1..20) {
            Text(
                text = "Item $i",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp) // Add padding to the text
            )
        }
    }
}







