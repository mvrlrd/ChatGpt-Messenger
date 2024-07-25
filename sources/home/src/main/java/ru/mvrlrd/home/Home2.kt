package ru.mvrlrd.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.home.di.DaggerHomeComponent
import ru.mvrlrd.main.pullrefresh.PullToRefreshLayout
import ru.mvrlrd.main.pullrefresh.rememberPullToRefreshState

@Composable
fun HomeScreen2(navController: NavController, chatId: Long) {
    val facade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()

    val homeComponent = remember {
//        throw RuntimeException()
        DaggerHomeComponent.builder().providersFacade(facade).build()
    }
    val repo = remember {
//        throw RuntimeException()
        homeComponent.getRepo()
    }

    val saveMessageToChatUseCase = remember {
        homeComponent.provideSaveMessageToChatUseCase()
    }
    val getAllMessagesForChatUseCase = remember {
        homeComponent.provideGetAllMessagesForChatUseCase()
    }


    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.createHomeViewModelFactory(
            repo,
            saveMessageToChatUseCase,
            getAllMessagesForChatUseCase,
            chatId
        )
    )

    val messages = viewModel.messages.collectAsState()

    Log.d("TAG","messages size  ${messages.value.size}")

    val userInput = remember { mutableStateOf(TextFieldValue()) }
    var response by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    response = viewModel.responseAnswer.observeAsState("").value
    val isLoading by viewModel.isLoading.observeAsState(false) // наблюдаем за состоянием загрузки

    val flow = viewModel.oneShotEventChannel.receiveAsFlow()



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
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
//                    .verticalScroll(scrollState)
            ) {
//                MessageBubble(message = Message(0,0,messages.value.toString(),1L,true))

              MessageList(messages = messages.value)


            }
            ShowToast(flow = flow)
            CustomTextField(
                textState = userInput,
                modifier = Modifier
                    .align(Alignment.End) // Выравнивание CustomTextField внизу экрана
            ) {
//                viewModel.saveMessageToChat(Message(holderChatId = chatId, text = userInput.value.text, date = 1L, isReceived = false ))
                viewModel.sendRequest(userInput.value.text)
            }
        }
    }


}

@Composable
fun MessageList(messages: List<Message>){
    val listState = rememberLazyListState()

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }


    LazyColumn(
        state=listState,
        modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        items(
            messages
        ){message->
            MessageBubble(message = message)
        }
    }
}


@Composable
fun MessageBubble(message: Message) {
    val bubbleColor = if (message.isReceived) Color(0xFFE0E0E0) else Color(0xFFDCF8C6)
    val alignment = if (message.isReceived) Alignment.CenterStart else Alignment.CenterEnd
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}



fun BubbleShape(
    cornerRadius: Dp = 16.dp,
    isReceived: Boolean
) = GenericShape { size, _ ->
    val cornerSize = cornerRadius.value
    if (isReceived) {
        // Received message shape
        moveTo(0f, cornerSize)
        arcTo(
            rect = Rect(0f, 0f, cornerSize, cornerSize),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(size.width - cornerSize, 0f)
        arcTo(
            rect = Rect(size.width - cornerSize, 0f, size.width, cornerSize),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(size.width, size.height - cornerSize)
        arcTo(
            rect = Rect(size.width - cornerSize, size.height - cornerSize, size.width, size.height),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(cornerSize, size.height)
        arcTo(
            rect = Rect(0f, size.height - cornerSize, cornerSize, size.height),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 45f,
            forceMoveTo = false
        )
        lineTo(0f, size.height)
        lineTo(0f, cornerSize)
    } else {
        // Sent message shape
        moveTo(0f, cornerSize)
        arcTo(
            rect = Rect(0f, 0f, cornerSize, cornerSize),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(size.width - cornerSize, 0f)
        arcTo(
            rect = Rect(size.width - cornerSize, 0f, size.width, cornerSize),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(size.width, size.height)
        lineTo(size.width - cornerSize, size.height)
        arcTo(
            rect = Rect(size.width - cornerSize, size.height - cornerSize, size.width, size.height),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 45f,
            forceMoveTo = false
        )
        lineTo(0f, size.height)
        lineTo(0f, cornerSize)
    }
}
