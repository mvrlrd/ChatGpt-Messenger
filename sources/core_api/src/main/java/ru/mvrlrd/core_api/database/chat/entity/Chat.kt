package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_rooms")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("chatId")val id: Long,
    val title: String,
    val date: String)
