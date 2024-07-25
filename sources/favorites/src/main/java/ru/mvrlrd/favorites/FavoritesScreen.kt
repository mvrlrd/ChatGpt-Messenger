package ru.mvrlrd.favorites


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.favorites.di.ChatRoomsComponent
import ru.mvrlrd.favorites.domain.ChatEntity


@Composable
fun FavoritesScreen(providersFacade: ProvidersFacade, onClick: (Long)-> Unit) {
    val chatRoomsComponent = remember {
        ChatRoomsComponent.create(providersFacade)
    }
    val viewModel: ChatRoomsViewModel = viewModel(
        factory = ChatRoomsViewModel.MyViewModelFactory(
            chatRoomsComponent.getAllChatsUseCase(),
            chatRoomsComponent.createChatUseCase(),
            chatRoomsComponent.removeChatUseCase()
        )
    )
    val itemList = remember { viewModel.items }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.createChat(ChatEntity(0, "hello one", 12))
                viewModel.getAllChats()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Card")
            }
        },
        content = { paddingValues ->
            CardList(cards = itemList, modifier = Modifier.padding(paddingValues), viewModel = viewModel) { id ->
                onClick(id)
            }
        }
    )
}

@Composable
fun CardList(
    cards: SnapshotStateList<ChatEntity>,
    modifier: Modifier,
    viewModel: ChatRoomsViewModel,
    onClick: (id: Long) -> Unit
) {
    LazyColumn {
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
fun SwipeToDismissCard(item: ChatEntity, onDismiss: ()->Unit, onClick: (id: Long) -> Unit) {
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
                StartToEnd -> Color.Blue
                EndToStart -> Color.Red
                null -> Color.Transparent
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Delete", color = Color.Black)
            }
        },
        dismissContent = {
            RoundedCard(title = item.title) {
                onClick(item.chatId)
            }
        }
    )
}

@Composable
fun RoundedCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.small,
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.h6,
                fontSize = 36.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    RoundedCard(title = "Hello", {})
}
