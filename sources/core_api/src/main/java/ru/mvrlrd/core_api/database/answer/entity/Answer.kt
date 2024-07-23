package ru.mvrlrd.core_api.database.answer.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ANSWERS")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val question: String,
    val answerText: String
)
