package ru.mvrlrd.feature_chat

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mvrlrd.base_chat_home.model.composables.ShowToast
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.feature_chat.di.DaggerHomeComponent

@Composable
fun ChatScreen(modifier: Modifier, chatId: Long) {
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
    val getChatSettingsUseCase = remember {
        homeComponent.provideGetChatUseCase()
    }

    val viewModel: ChatViewModel = viewModel(
        factory = ChatViewModel.createHomeViewModelFactory(
            getAnswerUseCase,
            saveMessageToChatUseCase,
            getAllMessagesForChatUseCase,
            deleteMessageUseCase,
            clearMessagesUseCase,
            getChatSettingsUseCase,
            chatId
        )
    )

    val messages = remember {
        viewModel.messageEntities
    }

    val oneShotEvent = viewModel.oneShotEventChannel.receiveAsFlow()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MessageList(messageEntities = messages) {
                viewModel.deleteMessageFromDatabase(it)
            }
            CustomTextField(
                modifier = Modifier
                    .zIndex(1f)
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
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


@Composable
fun MessageList(messageEntities: SnapshotStateList<MessageEntity>, onDismiss: (Long) -> Unit) {
    val listState = rememberLazyListState()
    var previousSize by remember { mutableStateOf(messageEntities.size) }
    LaunchedEffect(messageEntities.size) {
        if (messageEntities.size > previousSize) {
            listState.animateScrollToItem(messageEntities.size - 1)
        }
        previousSize = messageEntities.size
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 0.dp,
                top = 0.dp,
                end = dimensionResource(id = R.dimen.padding_big),
                start = dimensionResource(id = R.dimen.padding_big)
            )
    ) {
        itemsIndexed(
            items = messageEntities,
            key = { _, item ->
                item
            }
        ) { i, item ->
            val prev = if (i > 0) messageEntities[i - 1].isReceived else !item.isReceived
            val next = if (i < messageEntities.lastIndex) messageEntities[i + 1].isReceived else !item.isReceived
            SwipeToDismissCloudMessage(item = item, prev = prev, next = next) {
                onDismiss(it)
            }
        }
        item {
            Spacer(modifier = Modifier.height(72.dp)) // отступ от посл эл-та до нижнего края
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissCloudMessage(
    item: MessageEntity,
    prev: Boolean,
    next: Boolean,
    onDismiss: (Long) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.EndToStart) {
                onDismiss(item.id)
            }
            true
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {}
    ) {
        CloudMessage(messageEntity = item, prev = prev, next = next)
    }
}



@Composable
fun CloudMessage(messageEntity: MessageEntity, prev: Boolean, next: Boolean) {
    val cloudColor =
        if (messageEntity.isReceived) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondary
    val alignment = if (messageEntity.isReceived) Alignment.CenterStart else Alignment.CenterEnd
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (messageEntity.isReceived && !prev) 2.dp else if (messageEntity.isReceived != prev) 8.dp else 2.dp,
                bottom = if (!messageEntity.isReceived && next) 2.dp else if (messageEntity.isReceived != next) 8.dp else 2.dp
            ),
        contentAlignment = alignment
    ) {
        CloudCard(
            color = cloudColor,
            text = messageEntity.text,
            isReceived = messageEntity.isReceived,
            prev = prev,
            next = next
        )
    }
}

fun cloudShape(
    density: Density,
    isReceived: Boolean,
    prev: Boolean,
    next: Boolean,
    inset: Dp = 12.dp,
    cornerRadius: Dp = 16.dp
): GenericShape {
    return GenericShape { size: Size, _ ->
        val w = size.width
        val h = size.height
        val doDrawHvost = if (isReceived && !prev) {
            true
        } else !isReceived && next
        val _inset = if (doDrawHvost) with(density) { inset.toPx() } else 0f
        val radius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            if (isReceived) {
                if (doDrawHvost) {
                    moveTo(0f, 0f)
                    lineTo(_inset, _inset)
                    lineTo(w - radius, _inset)
                } else {
                    moveTo(0f, radius)
                    //top left
                    arcTo(
                        rect = Rect(
                            Offset(0f, 0f),
                            Offset(radius, radius)
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
                //top right
                arcTo(
                    rect = Rect(
                        Offset(w - radius, _inset),
                        Offset(w, _inset + radius)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(w, h - radius)
                //bottom right
                if (!next) {
                    arcTo(
                        rect = Rect(
                            Offset(w - radius * 3, h - radius * 3),
                            Offset(w, h)
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                } else {
                    arcTo(
                        rect = Rect(
                            Offset(w - radius, h - radius),
                            Offset(w, h)
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
                lineTo(radius, h)
                //bottom left
                arcTo(
                    rect = Rect(
                        Offset(0f, h - radius),
                        Offset(radius, h)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(0f, 0f)
            } else {
                moveTo(0f, radius)
                //top left
                arcTo(
                    rect = Rect(
                        Offset(0f, 0f),
                        Offset(radius, radius)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(w, 0f)
                //top right
                if (prev) {
                    arcTo(
                        rect = Rect(
                            Offset(w - radius * 3, 0f),
                            Offset(w, radius * 3)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                } else {
                    arcTo(
                        rect = Rect(
                            Offset(w - radius, 0f),
                            Offset(w, radius)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
                lineTo(w, h)
                if (doDrawHvost) {
                    lineTo(w - _inset, h - _inset)
                    lineTo(radius, h - _inset)
                } else {
                    lineTo(w, h - radius)
                    //bottom right
                    arcTo(
                        rect = Rect(
                            Offset(w - radius, h - radius),
                            Offset(w, h)
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
                //bottom left
                arcTo(
                    rect = Rect(
                        Offset(0f, h - radius - _inset),
                        Offset(radius, h - _inset)
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
    isReceived: Boolean,
    prev: Boolean,
    next: Boolean
) {
    val density = LocalDensity.current
    Card(
        shape = cloudShape(
            density = density,
            isReceived = isReceived,
            inset = dimensionResource(id = R.dimen.bubble_inset),
            cornerRadius = dimensionResource(id = R.dimen.corner_size),
            prev = prev,
            next = next
        ),
        colors = CardColors(containerColor = color, color,color,color),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = if (isReceived && !prev) dimensionResource(id = R.dimen.padding_medium) + dimensionResource(
                        id = R.dimen.bubble_inset
                    ) else dimensionResource(id = R.dimen.padding_medium),
                    bottom = if (!isReceived && !next || isReceived) dimensionResource(id = R.dimen.padding_medium) else dimensionResource(
                        id = R.dimen.padding_medium
                    ) + dimensionResource(
                        id = R.dimen.bubble_inset
                    )
                )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
@Preview
fun TestDraw() {
    CloudCard(
        text = "hello how mpos?",
        color = Color.Green, isReceived = true, prev = false, next = false
    )

}




