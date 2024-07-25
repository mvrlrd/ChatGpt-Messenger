package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = Chat::class,
        parentColumns = arrayOf("chatId"),
        childColumns = arrayOf("holderChatId"),
        onDelete = CASCADE
    )])
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("messageId")val id: Long = 0,
    @ColumnInfo(index = true)
    val holderChatId: Long,
    val text: String,
    val date: Long,
    val isReceived: Boolean
)
