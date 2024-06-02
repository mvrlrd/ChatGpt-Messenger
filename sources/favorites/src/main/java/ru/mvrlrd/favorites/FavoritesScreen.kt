package ru.mvrlrd.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mvrlrd.core_api.database.entity.Answer


@Composable
fun FavoritesScreen(answers: List<Answer>){
    val answers = mutableStateOf(answers)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen
            .background(Color.Cyan) // Set the background color
            .padding(16.dp), // Add padding to the entire column
        horizontalAlignment = Alignment.CenterHorizontally // Center the items horizontally
    ) {
        items(answers.value) { answer ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = answer.question,
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
        }
    }
}


private fun mockAnswers(): List<Answer>{
    val list = mutableListOf<Answer>()
    repeat(100){
        list.add(Answer(it.toLong(), "how much?", "lalalallalalallalalallalalallsdaldlasldlafnsdkjgbdjfhbvkjsbfckjwnkcndkjnvkshhfoiajsdlkjvnzkljbvkjznvm,c bkjajlefjaljksdnvlkjxnbkmvx "))
    }
    return list
}

