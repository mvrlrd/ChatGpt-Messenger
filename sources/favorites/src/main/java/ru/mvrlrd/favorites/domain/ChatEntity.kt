package ru.mvrlrd.favorites.domain

import java.io.Serializable


data class ChatEntity(
    val chatId: Long,
    val title: String,
    val date: Long
):Serializable
