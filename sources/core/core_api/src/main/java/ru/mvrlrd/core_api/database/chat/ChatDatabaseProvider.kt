package ru.mvrlrd.core_api.database.chat

interface ChatDatabaseProvider {
    fun provideContract(): ChatDatabaseContract
    fun provideChatDao(): ChatDao
}