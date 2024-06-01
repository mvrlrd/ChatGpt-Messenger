package ru.mvrlrd.core_api.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ANSWERS")
data class Answer(
    @PrimaryKey
    val id: Long,
    val question: String,
    val answerText: String
)
