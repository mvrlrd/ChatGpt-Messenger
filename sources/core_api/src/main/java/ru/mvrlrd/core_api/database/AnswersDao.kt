package ru.mvrlrd.core_api.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.mvrlrd.core_api.database.entity.Answer

@Dao
interface AnswersDao {
    @Query("SELECT * FROM ANSWERS")
    suspend fun getAnswers(): List<Answer>

    @Insert
    suspend fun createAnswer(answer: Answer)
}