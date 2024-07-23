package ru.mvrlrd.core_api.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("messageId")val id: Long,
    val text: String,
    val date: String
)
