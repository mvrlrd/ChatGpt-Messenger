package ru.mvrlrd.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.home.di.DaggerHomeComponent
import ru.mvrlrd.main.pullrefresh.PullToRefreshLayout
import ru.mvrlrd.main.pullrefresh.PullToRefreshLayoutState

@Composable
fun HomeScreen(chatId: Long, onToggleTheme: () -> Unit) {
    val facade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()

    val homeComponent = remember {
        DaggerHomeComponent.builder().providersFacade(facade).build()
    }
    val saveMessageToChatUseCase = remember {
        homeComponent.provideSaveMessageToChatUseCase()
    }
    val getAllMessagesForChatUseCase = remember {
        homeComponent.provideGetAllMessagesForChatUseCase()
    }
    val getAnswerUseCase = remember {
        homeComponent.provideGetAnswerUseCase()
    }
    val deleteMessageUseCase = remember {
        homeComponent.provideDeleteMessageUseCase()
    }
    val clearMessagesUseCase = remember {
        homeComponent.provideClearMessagesUseCase()
    }

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.createHomeViewModelFactory(
            getAnswerUseCase,
            saveMessageToChatUseCase,
            getAllMessagesForChatUseCase,
            deleteMessageUseCase,
            clearMessagesUseCase,
            chatId
        )
    )

    val messages = remember {
        viewModel.messages
    }
    var response by remember { mutableStateOf("") }
    response = viewModel.responseAnswer.observeAsState("").value
//    val isLoading by viewModel.isLoading.observeAsState(false)
    val oneShotEvent = viewModel.oneShotEventChannel.receiveAsFlow()

    PullToRefreshLayout(
        pullRefreshLayoutState = PullToRefreshLayoutState { "hello" },
        onRefresh = onToggleTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MessageList(messages = messages) {
                    viewModel.deleteMessage(it)
                }
                CustomTextField(
                    modifier = Modifier
                        .zIndex(1f)
                        .wrapContentSize()
                        .align(Alignment.BottomStart)
                        .padding(
                            bottom = 16.dp,
                            start = 16.dp, end = 16.dp
                        )
                ) {
                    viewModel.sendRequest(it)
                }
            }
            ShowToast(flow = oneShotEvent)
        }
    }
}

@Composable
fun MessageList(messages: SnapshotStateList<Message>, onDismiss: (Long) -> Unit) {
    val listState = rememberLazyListState()
    var previousSize by remember { mutableStateOf(messages.size) }
    LaunchedEffect(messages.size) {
        if (messages.size > previousSize) {
            listState.animateScrollToItem(messages.size - 1)
        }
        previousSize = messages.size
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 0.dp,
                top = dimensionResource(id = R.dimen.padding_big),
                end = dimensionResource(id = R.dimen.padding_big),
                start = dimensionResource(id = R.dimen.padding_big)
            )
    ) {
        itemsIndexed(
            items = messages,
            key = { _, item ->
                item
            }
        ) { _, item ->
            SwipeToDismissMessageBubble(item = item) {
                onDismiss(it)
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp)) // отступ от посл эл-та до нижнего края
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissMessageBubble(item: Message, onDismiss: (Long) -> Unit) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss(item.id)
            }
            true
        }
    )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(
            DismissDirection.EndToStart
        ),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
        },
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.EndToStart -> Color.Transparent
                else -> Color.Transparent
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(dimensionResource(id = R.dimen.padding_big)),
                contentAlignment = Alignment.CenterEnd
            ) {
                when (dismissState.dismissDirection) {
                    DismissDirection.EndToStart -> {
//                        Icon(
//                            painter = painterResource(id = ru.mvrlrd.uikit.R.drawable.baseline_delete_forever_24), // Replace with your drawable resource ID
//                            contentDescription = "Delete Icon",
//                            modifier = Modifier.size(50.dp)
//                        )
                    }

                    else -> {

                    }

                }
            }
        },
        dismissContent = {
            MessageBubble(message = item)
        }
    )
}


@Composable
fun MessageBubble(message: Message) {
    val bubbleColor =
        if (message.isReceived) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary

    val alignment = if (message.isReceived) Alignment.CenterStart else Alignment.CenterEnd

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_small)
            ),
        contentAlignment = alignment
    ) {
        if (message.isReceived) {
            BubbleWithArrow(
                bubble = { Bubble(color = bubbleColor, text = message.text, isReceived = true) },
                arrow = { Arrow(isReceived = true, color = bubbleColor) },
                isReceived = true
            )
        } else {
            BubbleWithArrow(
                bubble = { Bubble(color = bubbleColor, text = message.text, isReceived = false) },
                arrow = { Arrow(isReceived = false, color = bubbleColor) },
                isReceived = false
            )
        }
    }
}

@Composable
fun BubbleWithArrow(
    bubble: @Composable () -> Unit,
    arrow: @Composable () -> Unit,
    isReceived: Boolean
) {
    Row(modifier = Modifier
        .height(IntrinsicSize.Max)
    ) {
        if (isReceived) {
            arrow()
            bubble()
        } else {
            bubble()
            arrow()
        }
    }
}

@Composable
fun Bubble(color: Color, text: String, isReceived: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = if (isReceived) 0.dp else dimensionResource(id = R.dimen.corner_size),
                    topEnd = dimensionResource(id = R.dimen.corner_size),
                    bottomEnd = if (isReceived) dimensionResource(id = R.dimen.corner_size) else 0.dp,
                    bottomStart = dimensionResource(id = R.dimen.corner_size)
                )
            )
            .padding(dimensionResource(id = R.dimen.text_padding))
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Composable
private fun Arrow(color: Color, isReceived: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = TriangleEdgeShape(isReceived)
            )
            .width(dimensionResource(id = R.dimen.arrow_width))
            .fillMaxHeight()
    )
}


private class TriangleEdgeShape(private val isReceived: Boolean) : Shape {
    private val offset = 20
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            if (isReceived) {
                moveTo(x = size.width, y = 0f)
                lineTo(x = size.width, y = offset.toFloat())
                lineTo(x = size.width - offset, y = 0f)
            } else {
                moveTo(x = 0f, y = size.height - offset)
                lineTo(x = 0f, y = size.height)
                lineTo(x = 0f + offset, y = size.height)
            }
            close()
        }
        return Outline.Generic(path = trianglePath)
    }
}




