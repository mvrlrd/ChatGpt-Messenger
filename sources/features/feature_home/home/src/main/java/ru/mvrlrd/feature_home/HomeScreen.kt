package ru.mvrlrd.feature_home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.feature_home.di.ChatRoomsComponent


@Composable
fun HomeScreen(modifier: Modifier, onClickEdit: (Long)-> Unit, onClickCard: (Long) -> Unit) {


    val providersFacade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()
    val chatRoomsComponent = remember {
        ChatRoomsComponent.create(providersFacade)
    }
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.MyViewModelFactory(
            chatRoomsComponent.getAllChatsUseCase(),
            chatRoomsComponent.removeChatUseCase()
        )
    )
    val itemList = remember { viewModel.items }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onClickEdit(0L) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Card")
            }
        },
        content = { paddingValues ->
            CardList(
                cards = itemList,
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel,
                onClickEditButton = onClickEdit
            ) { id ->
                onClickCard(id)
            }
        }
    )
}

@Composable
fun CardList(
    cards: SnapshotStateList<Chat>,
    modifier: Modifier,
    viewModel: HomeViewModel,
    onClickEditButton: (Long) -> Unit,
    onClickCard: (id: Long) -> Unit
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
            SwipeToDismissCard(
                item = item,
                onClickEditButton = onClickEditButton,
                onDismiss = { viewModel.removeChat(item.chatId) }) {
                onClickCard(item.chatId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissCard(
    item: Chat,
    onDismiss: () -> Unit,
    onClickEditButton: (Long) -> Unit,
    onClickCard: (id: Long) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDismiss()
            }
            true
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {}
    ) {
        CharacterCard(
            chat = item,
            onClickEditButton = onClickEditButton
        ) {
            onClickCard(item.chatId)
        }
    }
}




