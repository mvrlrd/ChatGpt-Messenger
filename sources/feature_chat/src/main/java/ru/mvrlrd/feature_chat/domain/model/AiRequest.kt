package ru.mvrlrd.feature_chat.domain.model

import ru.mvrlrd.base_chat_home.model.CompletionOptions

data class Message(
    val role: String,
    val text: String
)

data class AiRequest(
    val completionOptions: CompletionOptions,
    val messages: List<Message>
)