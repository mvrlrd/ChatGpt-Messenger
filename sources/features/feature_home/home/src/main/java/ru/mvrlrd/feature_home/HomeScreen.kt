package ru.mvrlrd.feature_home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mvrlrd.feature_home.di.ChatRoomsComponent
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.core_api.mediators.AppWithFacade
import java.util.Date


@Composable
fun HomeScreen(modifier: Modifier, openSettings: (Long)->Unit, onClick: (Long) -> Unit) {

    val providersFacade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()
    val chatRoomsComponent = remember {
        ChatRoomsComponent.create(providersFacade)
    }
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.MyViewModelFactory(
            chatRoomsComponent.getAllChatsUseCase(),
            chatRoomsComponent.createChatUseCase(),
            chatRoomsComponent.removeChatUseCase()
        )
    )
    val itemList = remember { viewModel.items }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { openSettings(0L) }
//            {

//                viewModel.createChat(
//                    Chat(
//                        chatId = 0,
//                        title = "lalaland",
//                        roleText = "ты пяти летний ребенок",
//                        modelVer = "",
//                        completionOptions = CompletionOptions(
//                            stream = false,
//                            temperature = 0.9,
//                            maxTokens = 500
//                        ),
//                        date = Date().time
//                    )
//                )
//                viewModel.getAllChats()
//            }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Card")
            }
        },
        content = { paddingValues ->
            CardList(
                cards = itemList,
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            ) { id ->
                onClick(id)
            }
        }
    )
}

@Composable
fun CardList(
    cards: SnapshotStateList<Chat>,
    modifier: Modifier,
    viewModel: HomeViewModel,
    onClick: (id: Long) -> Unit
) {

    val listState = rememberLazyListState()
    var previousSize by remember { mutableStateOf(cards.size) }

    LaunchedEffect(cards.size) {
        if (cards.size > previousSize) {
            listState.animateScrollToItem(cards.size - 1)
        }
        previousSize = cards.size
    }

    LazyVerticalGrid(

        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        itemsIndexed(

            items = cards,
            key = { _, item ->
                item
            }
        ) { index, item ->
            SwipeToDismissCard(item = item, onDismiss = { viewModel.removeChat(item.chatId) }) {
                onClick(item.chatId)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissCard(item: Chat, onDismiss: () -> Unit, onClick: (id: Long) -> Unit) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss()
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
            FractionalThreshold(if (direction == EndToStart) 0.1f else 0.05f)
        },
        background = {
            val color = when (dismissState.dismissDirection) {
                StartToEnd -> Color.Transparent
                EndToStart -> Color.Transparent
                else -> Color.Transparent
            }
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(color)
//                    .padding(16.dp),
//                contentAlignment = Alignment.CenterEnd
//            ) {
////                Text("Delete", color = Color.Black)
//            }
        },
        dismissContent = {
            CharacterCard(
                name = item.title
            ) {
                onClick(item.chatId)
            }
        }
    )
}



