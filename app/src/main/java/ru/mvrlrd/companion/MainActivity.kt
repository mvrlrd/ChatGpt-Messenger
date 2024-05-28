package ru.mvrlrd.companion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            Log.d("Activity", remoteRepository.getAnswer("", "do u like ice cream?"))
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
                label = { Text("Enter your query") },
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


            Text(
                text = response,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }
}
