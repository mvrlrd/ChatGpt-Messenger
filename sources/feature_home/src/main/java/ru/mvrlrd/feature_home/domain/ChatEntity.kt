package ru.mvrlrd.feature_home.domain

import java.io.Serializable


data class ChatEntity(
    val chatId: Long,
    val title: String,
    val role: String,
    val completionOptions: CompletionOptionsEntity = CompletionOptionsEntity(),
    val modelVer: String,
    val usageEntity: UsageEntity = UsageEntity(),
    val date: Long
):Serializable


data class UsageEntity(
    val inputTokens: Int = 0,
    val completionTokens: Int = 0,
    val totalTokens: Int = 0,
)
data class CompletionOptionsEntity(
    val stream: Boolean = false,
    val temperature: Double = 0.3,
    val maxTokens: Int = 1000
)



