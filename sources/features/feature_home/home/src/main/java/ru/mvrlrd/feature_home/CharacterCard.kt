package ru.mvrlrd.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Usage

@Composable
fun CharacterCard(
    imagePainter: Painter = painterResource(id = R.drawable.dark_robot),
    chat: Chat,
    onClickEditButton: (Long)->Unit,
    onClickCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable { onClickCard() },
        shape = RoundedCornerShape(16.dp),
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
                        text = chat.title,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                    Text(
                        text = chat.roleText,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Image(painter = painterResource(id = R.drawable.baseline_mode_edit_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            onClickEditButton.invoke(chat.chatId)
                        },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        imagePainter = painterResource(id = R.drawable.dark_robot),
        chat = Chat(
            chatId = 1L,
            title = "title",
            roleText = "role",
            completionOptions = CompletionOptions(
                stream = true,
                temperature = 0.6,
                maxTokens = 1000
            ),
            modelVer = "1.0",
            prompt = true,
            usage = Usage(
                inputTokens = 1000,
                completionTokens = 300,
                totalTokens = 1300
            )
        ),
        onClickEditButton = {}
    ) {

    }
}