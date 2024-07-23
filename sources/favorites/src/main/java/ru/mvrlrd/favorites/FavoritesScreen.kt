package ru.mvrlrd.favorites

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import ru.mvrlrd.core_api.database.entity.Answer


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.favorites.di.ChatRoomsComponent
import ru.mvrlrd.favorites.di.DaggerChatRoomsComponent
import ru.mvrlrd.favorites.domain.ChatEntity


@Composable
fun FavoritesScreen(providersFacade: ProvidersFacade) {
val scope =   rememberCoroutineScope()
    val chatRoomsComponent = remember {
        ChatRoomsComponent.create(providersFacade)
    }

    val viewModel: ChatRoomsViewModel = viewModel(
        factory = ChatRoomsViewModel.MyViewModelFactory(
            chatRoomsComponent.getAllChatsUseCase(), chatRoomsComponent.createChatUseCase(),
        )
    )
    val itemList = remember { viewModel.items }





//    val cards = remember { mutableStateListOf<String>() }
    var counter = 0
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.createChat(ChatEntity(0, "hello one", "12"))
                    viewModel.getAllChats()
//                cards.add("${ counter++}")


            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Card")
            }
        },
        content = { paddingValues ->
            CardList(cards = itemList, modifier = Modifier.padding(paddingValues))
        }
    )
}

    @Composable
    fun CardList(cards: SnapshotStateList<ChatEntity>, modifier: Modifier) {
        LazyColumn {
            itemsIndexed(
                items = cards,
                key = {index, item ->
                    item
                }
            ){index, item ->  
                SwipeToDismissCard(title = item.title) {
                    cards.remove(item)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SwipeToDismissCard(title: String, onDismiss: () -> Unit) {
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
                val color = when(dismissState.dismissDirection){
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
               RoundedCard(title = title) {
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
