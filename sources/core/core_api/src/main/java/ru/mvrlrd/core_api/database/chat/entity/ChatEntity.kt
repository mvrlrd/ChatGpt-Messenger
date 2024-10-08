package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "chat_rooms")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("chatId") val id: Long = 0,
    val title: String,
    val roleText: String,
    val prompt: Boolean,
    @Embedded
    val completionOptions: CompletionOptionsEntity,
    val modelVer: String,
    @Embedded
    val usage: UsageEntity,
    val date: Long = Date().time
)


data class CompletionOptionsEntity(
    val stream: Boolean = false,
    val temperature: Double,
    val maxTokens: Int
)

data class UsageEntity(
    val inputTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
)


