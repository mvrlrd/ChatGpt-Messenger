package ru.mvrlrd.core_api.database.chat

interface ChatDatabaseContract {
    fun chatDao(): ChatDao
}