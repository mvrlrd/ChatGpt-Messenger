package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = ChatEntity::class,
        parentColumns = arrayOf("chatId"),
        childColumns = arrayOf("holderChatId"),
        onDelete = CASCADE
    )])
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("messageId")val id: Long = 0,
    @ColumnInfo(index = true)
    val holderChatId: Long,
    val text: String,
    val date: Long = Date().time,
    val isReceived: Boolean = true
): Serializable
