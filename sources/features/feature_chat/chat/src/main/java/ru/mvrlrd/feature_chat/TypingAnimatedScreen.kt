package ru.mvrlrd.feature_chat

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay


@Composable
fun TypingAnimation(text: String, isLoading: Boolean) {
    var displayedText by remember { mutableStateOf("") }
    var visibleTextLength by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            visibleTextLength =  0
            CircularProgressIndicator() // Круговой прогресс-бар
        } else {
            LaunchedEffect(text) {
                for (i in text.indices) {
                    delay(50)
                    visibleTextLength = i + 1
                }
            }
            displayedText = text.take(visibleTextLength)

            BasicText(
                text = displayedText,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.2f, // Увеличиваем размер шрифта на 20%
                    color = MaterialTheme.colorScheme.primary // Цвет текста противоположен основному цвету темы
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
            )
        }
    }
}



