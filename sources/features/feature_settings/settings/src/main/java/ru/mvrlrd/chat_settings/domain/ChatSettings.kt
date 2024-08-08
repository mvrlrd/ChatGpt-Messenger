package ru.mvrlrd.chat_settings.domain

import ru.mvrlrd.chat_settings.MaxTokens
import ru.mvrlrd.chat_settings.Temperature

data class ChatSettings(
    val chatId: Long,
    val name: String,
    val systemRole: String,
    val maxTokens: MaxTokens = MaxTokens(""),
    val stream: Boolean=false,
    val temperature: Temperature = Temperature(0.3f)
)
