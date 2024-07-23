package ru.mvrlrd.core_api.database.answer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mvrlrd.core_api.database.answer.entity.Answer

@Dao
interface AnswersDao {
    @Query("SELECT * FROM ANSWERS")
    suspend fun getAnswers(): List<Answer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAnswer(answer: Answer): Long
}