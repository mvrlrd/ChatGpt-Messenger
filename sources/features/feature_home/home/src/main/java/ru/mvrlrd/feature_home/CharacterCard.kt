package ru.mvrlrd.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Usage
import ru.mvrlrd.feature_home.domain.ChatForHome

@Composable
fun CharacterCard(
    chat: ChatForHome,
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
                TextIcon(modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterStart),
                    text = getInitialsFromName(chat.title),
                    color = colorResource(chat.color)
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
                    text = chat.role,
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

private fun getInitialsFromName(s: String): String{
    if (s.isBlank()) return "?"
    if (s.length==1){
        return s.uppercase()
    }else{
        val arr = s.trim().split(" ")
        val str = StringBuilder()
        arr.take(2).forEach{
            if (it.isNotBlank()){
                str.append(it.first().uppercase())
            }
        }
        if (str.length == 1){
            str.append(s[1])
        }
        return str.toString()
    }
}



@Composable
fun TextIcon(modifier: Modifier, text: String, color: Color){
    Box(
        modifier = modifier// Размер иконки
            .background(color, CircleShape), // Цвет фона и форма
        contentAlignment = Alignment.Center // Центрируем текст
    ) {
        BasicText(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, // Цвет текста
                fontSize = 24.sp, // Размер шрифта
                textAlign = TextAlign.Center // Выравнивание текста
            )
        )
    }
}

@Composable
@Preview
fun TextIconPreview(){
    TextIcon(Modifier,"IB", Color.Green)
}

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        chat = ChatForHome(
            chatId = 1L,
            title = "title",
            role = "role",
            color = R.color.one,
            prompt = true,
        ),
        onClickEditButton = {}
    ) {

    }
}