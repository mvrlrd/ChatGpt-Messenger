package ru.mvrlrd.core_api.database

interface DatabaseProvider {

    fun provideDatabase(): AnswersDatabaseContract

    fun habitsDao(): AnswersDao
}