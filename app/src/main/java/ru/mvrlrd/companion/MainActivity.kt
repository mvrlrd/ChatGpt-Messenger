package ru.mvrlrd.companion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mvrlrd.companion.ui.theme.CompanionTheme
import ru.mvrlrd.core_api.NetworkClient
import ru.mvrlrd.ktor_module.KtorClient
import ru.mvrlrd.ktor_module.RemoteRepository

class MainActivity : ComponentActivity() {

    val client: NetworkClient = KtorClient()
    val remoteRepository: RemoteRepository = RemoteRepository(client)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            Log.d("Activity", remoteRepository.getAnswer("Сколько лет Земле?"))
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompanionTheme {
        Greeting("Android")
    }
}