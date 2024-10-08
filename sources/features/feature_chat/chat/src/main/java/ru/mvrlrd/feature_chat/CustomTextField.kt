package ru.mvrlrd.feature_chat

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit
) {
    val userInput = remember { mutableStateOf(TextFieldValue()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        BasicTextField(
            value = userInput.value,
            onValueChange = { userInput.value = it },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        IconButton(
            enabled = userInput.value.text.isNotBlank(),
            onClick = {
                    Log.d("TAG", "clicked")
                    onSend(userInput.value.text)
                    userInput.value = TextFieldValue("")
            },
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_send),
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
@Preview
fun CustomTextFieldPreview(){
    CustomTextField {

    }
}