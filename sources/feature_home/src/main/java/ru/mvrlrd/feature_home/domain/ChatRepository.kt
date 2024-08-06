package ru.mvrlrd.feature_home.domain

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getAllChats(): Flow<List<ChatEntity>>
    suspend fun createChat(entity: ChatEntity)
    suspend fun removeChat(id: Long)
}