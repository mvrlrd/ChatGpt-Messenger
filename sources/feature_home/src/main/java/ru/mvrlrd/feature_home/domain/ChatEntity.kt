package ru.mvrlrd.feature_home.domain

import java.io.Serializable


data class ChatEntity(
    val chatId: Long,
    val title: String,
    val date: Long
):Serializable
