package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.Relation

data class ChatWithMessages (
    val chat: Chat,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "messageId"
    )
    val messages: List<Message>
)