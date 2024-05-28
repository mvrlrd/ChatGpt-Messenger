package ru.mvrlrd.core_api.network

interface RemoteRepository {
    suspend fun getAnswer(systemRole: String,query: String): String
}