package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatWithMessages (
    @Embedded
    val chat: Chat,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "holderChatId"
    )
    val messages: List<Message>
)