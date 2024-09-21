package ru.mvrlrd.feature_chat.domain

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.feature_chat.domain.model.AIResponse
import ru.mvrlrd.feature_chat.domain.model.AiRequest

interface ChatRepository {
    fun getAllMessages(chatId: Long): Flow<List<MessageEntity>>
    suspend fun saveMessage(messageEntity: MessageEntity)
    suspend fun deleteMessage(messageId: Long)
    suspend fun clearChat(chatId: Long)
    suspend fun getAnswer(aiRequest: AiRequest,chatId: Long, prompt: Boolean): Result<AIResponse>
    suspend fun getChatSettings(chatId: Long): Result<Chat>
}