package ru.mvrlrd.feature_home.domain

import java.io.Serializable

data class ChatForHome(
    val chatId: Long,
    val title: String,
    val role: String,
    val prompt: Boolean,
    val color: Int
): Serializable