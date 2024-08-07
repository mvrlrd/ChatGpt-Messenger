package ru.mvrlrd.feature_home.domain

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat

interface ChatRepository {
    suspend fun getAllChats(): Flow<List<Chat>>
    suspend fun createChat(entity: Chat)
    suspend fun removeChat(id: Long)
}