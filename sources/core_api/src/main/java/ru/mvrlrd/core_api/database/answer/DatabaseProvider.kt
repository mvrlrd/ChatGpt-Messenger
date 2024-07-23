package ru.mvrlrd.core_api.database.answer

interface DatabaseProvider {

    fun provideDatabase(): AnswersDatabaseContract

    fun answersDao(): AnswersDao
}