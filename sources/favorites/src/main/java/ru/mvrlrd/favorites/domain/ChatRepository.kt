package ru.mvrlrd.favorites.domain

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getAllChats(): Flow<List<ChatEntity>>
    suspend fun createChat(entity: ChatEntity)
}