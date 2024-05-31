package ru.mvrlrd.core_impl.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.mvrlrd.core_api.database.AnswersDao
import ru.mvrlrd.core_api.database.AnswersDatabaseContract
import javax.inject.Singleton

const val ANSWERS_DATABASE_NAME = "ANSWERS_DB"

@Module
class DatabaseModule {

    @Provides
    @Reusable
    fun provideHabitsDao(habitsDatabaseContract: AnswersDatabaseContract): AnswersDao {
        return habitsDatabaseContract.habitsDao()
    }

    @Provides
    @Singleton
    fun provideHabitsDatabase(context: Context): AnswersDatabaseContract {
        return Room.databaseBuilder(
            context,
            AnswersDatabase::class.java, ANSWERS_DATABASE_NAME
        ).build()
    }
}
