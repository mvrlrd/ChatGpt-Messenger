package ru.mvrlrd.base_chat_home.model

import java.io.Serializable


data class Chat(
    val chatId: Long,
    val title: String,
    val roleText: String,
    val completionOptions: CompletionOptions = CompletionOptions(),
    val modelVer: String,
    val usage: Usage = Usage(),
    val date: Long
):Serializable


data class Usage(
    val inputTokens: Int = 0,
    val completionTokens: Int = 0,
    val totalTokens: Int = 0,
)
data class CompletionOptions(
    val stream: Boolean = false,
    val temperature: Double = 0.6,
    val maxTokens: Int = 2000
)



