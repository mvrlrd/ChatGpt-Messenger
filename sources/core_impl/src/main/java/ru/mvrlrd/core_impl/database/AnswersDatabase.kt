package ru.mvrlrd.core_impl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mvrlrd.core_api.database.answer.AnswersDatabaseContract
import ru.mvrlrd.core_api.database.answer.entity.Answer

@Database(entities = [Answer::class], version = 2)
abstract class AnswersDatabase : RoomDatabase(), AnswersDatabaseContract