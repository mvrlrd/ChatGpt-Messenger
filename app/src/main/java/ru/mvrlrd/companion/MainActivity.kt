package ru.mvrlrd.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import ru.mvrlrd.companion.ui.theme.CompanionTheme
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.core_api.network.RemoteRepository
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.createMainViewModelFactory(remoteRepository)
        )[MainViewModel::class]
    }

    private val component: MainComponent by lazy {
        MainComponent.create((application as AppWithFacade).getFacade())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContent {
            CompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }


    @Composable
    fun MyApp() {
        var userInput by remember { mutableStateOf("") }
        var response by remember { mutableStateOf("") }
        viewModel.responseAnswer.observe(this){
            response = it
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("спроси меня") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.sendRequest(userInput)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TypingAnimation(text = response )
        }
    }

    @Composable
    fun TypingAnimation(text: String) {

        var displayedText by remember { mutableStateOf("") }
        var visibleTextLength by remember { mutableStateOf(0) }

        LaunchedEffect(text) {
            for (i in text.indices) {
                delay(50)
                visibleTextLength = i + 1
            }
        }

        displayedText = text.take(visibleTextLength)

        BasicText(
            text = displayedText,
            style = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }
}

