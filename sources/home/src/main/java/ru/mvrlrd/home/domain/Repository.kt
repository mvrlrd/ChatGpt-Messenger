package ru.mvrlrd.home.domain

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.core_api.network.dto.MyResponse

interface Repository {
    fun getAllMessages(chatId: Long): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
    suspend fun deleteMessage(messageId: Long)
    suspend fun clearChat(chatId: Long)

    suspend fun getAnswer(systemRole: String, query: String): Result<AIResponse>
}