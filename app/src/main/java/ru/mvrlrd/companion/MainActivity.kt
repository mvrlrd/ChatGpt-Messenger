package ru.mvrlrd.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        val userInput = remember { mutableStateOf(TextFieldValue()) }
        var response by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()
        viewModel.responseAnswer.observe(this) {
            response = it
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),

            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                TypingAnimation(text = response)
            }
//            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                textState = userInput,
                modifier = Modifier
                    .align(Alignment.End) // Выравнивание CustomTextField внизу экрана
            ) {
                viewModel.sendRequest(
                    "дай длинный ответ что такое счастье"
//                    userInput.value.text
                )
            }
        }
    }


    @Composable
    fun TypingAnimation(text: String) {
        var displayedText by remember { mutableStateOf("") }
        var visibleTextLength by remember { mutableStateOf(0) }
        LaunchedEffect(text) {
            for (i in text.indices) {
                delay(1)
                visibleTextLength = i + 1
            }
        }
        displayedText = text.take(visibleTextLength)
        BasicText(
            text = displayedText,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            ),
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
        )
    }

    @Composable
    fun CustomTextField(
        modifier: Modifier = Modifier,
        textState: MutableState<TextFieldValue>,
        onSend: () -> Unit
    ) {

        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
//                        .padding(16.dp)
                    ,
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        ) {
                        BasicTextField(
                            value = textState.value,
                            onValueChange = { textState.value = it },
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                        IconButton(
                            onClick = onSend,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.Blue)
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_send), // Use your own icon here
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }





    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }

    @Composable
    @Preview(showBackground = true)
    fun CustomTextFieldPreview() {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextField(
                        textState = textState,
                        onSend = {
                            // Handle send action
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}



