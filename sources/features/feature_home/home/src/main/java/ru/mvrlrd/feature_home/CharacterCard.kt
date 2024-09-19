package ru.mvrlrd.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onClickCard() },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp)

            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                )

            }

            Column(
                modifier = Modifier
                    .padding(
                        start = 15.dp,
                        top = 10.dp
                    )
                    .weight(1f)
            ) {
                Row {
                    Text(
                        text = chat.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val image = if (chat.prompt) painterResource(id = ru.mvrlrd.base_chat_home.R.drawable.icons8_flash_96) else painterResource(id = ru.mvrlrd.base_chat_home.R.drawable.icons8_connection_50)
                    Box(modifier = Modifier
                        .padding(start = 10.dp)){
                        Image(
                            painter = image,
                            contentDescription = "Character Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(24.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }

                }

                Text(
                    text = chat.roleText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_mode_edit_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onClickEditButton.invoke(chat.chatId)
                        }
                        ,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    ,
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