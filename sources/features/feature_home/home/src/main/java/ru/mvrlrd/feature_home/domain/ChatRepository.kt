package ru.mvrlrd.feature_home.domain

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getAllChats(): Flow<List<ChatForHome>>
    suspend fun removeChat(id: Long)
}