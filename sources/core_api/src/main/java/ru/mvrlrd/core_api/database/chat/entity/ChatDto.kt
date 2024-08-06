package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_rooms")
data class ChatDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("chatId") val id: Long = 0,
    val title: String,
    val role: String,
    @Embedded
    val completionOptions: CompletionOptionsDto,
    val modelVer: String,
    @Embedded
    val usage: UsageDto,
    val date: Long
)


data class CompletionOptionsDto(
    val stream: Boolean = false,
    val temperature: Double,
    val maxTokens: Int
)

data class UsageDto(
    val inputTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
)


