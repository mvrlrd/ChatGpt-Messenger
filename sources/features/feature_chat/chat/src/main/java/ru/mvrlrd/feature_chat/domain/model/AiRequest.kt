package ru.mvrlrd.feature_chat.domain.model

import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Message

data class AiRequest(
    val completionOptions: CompletionOptions,
    val messages: List<Message>
)