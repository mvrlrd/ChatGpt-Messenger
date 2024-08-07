package ru.mvrlrd.feature_home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_home.di.ChatRoomsComponent
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import java.util.Date


@Composable
fun HomeScreen(modifier: Modifier, providersFacade: ProvidersFacade, onClick: (Long) -> Unit) {
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

//data class Chat(
//    val chatId: Long,
//    val title: String,
//    val role: String="system",
//    val roleText: String= "ты умный ассистент",
//    val completionOptions: CompletionOptions = CompletionOptions(),
//    val modelVer: String,
//    val usage: Usage = Usage(),
//    val date: Long
//):Serializable
//
//
//data class Usage(
//    val inputTokens: Int = 0,
//    val completionTokens: Int = 0,
//    val totalTokens: Int = 0,
//)
//data class CompletionOptions(
//    val stream: Boolean = false,
//    val temperature: Double = 0.6,
//    val maxTokens: Int = 2000
//)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.createChat(
                    Chat(
                        chatId = 0,
                        title = "lalaland",
                        roleText = "ты пяти летний ребенок",
                        modelVer = "",
                        completionOptions = CompletionOptions(
                            stream = false,
                            temperature = 0.9,
                            maxTokens = 500
                        ),
                        date = Date().time
                    )
                )
                viewModel.getAllChats()
            }) {
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


@Composable
fun CharacterCard(
    imagePainter: Painter = painterResource(id = R.drawable.dark_robot),
    name: String,
    profession: String = "software engineer",
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = Color.Transparent,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.7f)
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = name,

                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = profession,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        imagePainter = painterResource(id = R.drawable.dark_robot),
        name = "John Doe",
        profession = "Software Engineer",
        {}
    )
}
