package ru.mvrlrd.feature_chat.domain

import java.util.Date


data class AIResponse(
    val answer: String,
    val role: String,
    val inputTextTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
    val modelVersion: String,
    val date: Long = Date().time
)