package ru.mvrlrd.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Card
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
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
            CloudMessage(message = item)
        }
    )
}


@Composable
fun CloudMessage(message: Message) {
    val cloudColor =
        if (message.isReceived) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary
    val alignment = if (message.isReceived) Alignment.CenterStart else Alignment.CenterEnd
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_small)
            ),
        contentAlignment = alignment
    ) {
        CloudCard(color = cloudColor, text = message.text, isReceived = message.isReceived)
    }
}

fun cloudShape(density: Density, isReceived: Boolean, inset: Float = 12f, cornerRadius: Float = 16f): GenericShape {
    return GenericShape { size: Size, _ ->
        val dpToPx = { dpValue: Float -> dpValue * density.density }
        val w = size.width
        val h = size.height
        val _inset = dpToPx(inset)
        val r = dpToPx(cornerRadius)
        val path = Path().apply {
            if(isReceived){
                moveTo(0f, 0f)
                lineTo(_inset, _inset)
                lineTo(w - r, _inset)
                //top right
                arcTo(
                    rect = Rect(
                        Offset(w - r, _inset),
                        Offset(w, _inset+r)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(w, h - r )
                //bottom right
                arcTo(
                    rect = Rect(
                        Offset(w -r , h-r),
                        Offset(w, h)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(r, h)
                //bottom left
                arcTo(
                    rect = Rect(
                        Offset(0f, h-r),
                        Offset(r, h)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(0f, 0f)

            }else{
                moveTo(0f, r)
                //top left
                arcTo(
                    rect = Rect(
                        Offset(0f, 0f),
                        Offset(r, r)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(w, 0f)
                //top right
                arcTo(
                    rect = Rect(
                        Offset(w -r, 0f),
                        Offset(w, r)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(w, h)
                lineTo(w - _inset, h-_inset)
                lineTo(r, h - _inset)
                //bottom left
                arcTo(
                    rect = Rect(
                        Offset(0f, h-r-_inset),
                        Offset(r, h-_inset)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            close()
        }
        addPath(path)
    }
}


@Composable
fun CloudCard(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    isReceived: Boolean
) {
    val density = LocalDensity.current
    Card(
        shape = cloudShape(
            density = density,
            isReceived = isReceived,
            inset = 12f,
            cornerRadius = 16f
        ),
        modifier = modifier,
        backgroundColor = color,
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = if (isReceived) dimensionResource(id = R.dimen.padding_medium) + dimensionResource(
                        id = R.dimen.bubble_inset
                    ) else dimensionResource(id = R.dimen.padding_medium),
                    bottom = if (isReceived) dimensionResource(id = R.dimen.padding_medium) else dimensionResource(id = R.dimen.padding_medium) + dimensionResource(
                        id = R.dimen.bubble_inset)
                )
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}



@Composable
@Preview
fun TestDraw(){
    CloudCard(text = "hello how mpos?",
        color = Color.Green, isReceived = false )

}




