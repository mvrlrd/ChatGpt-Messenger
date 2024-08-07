package ru.mvrlrd.feature_chat.domain

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity

interface ChatRepository {
    fun getAllMessages(chatId: Long): Flow<List<MessageEntity>>
    suspend fun saveMessage(messageEntity: MessageEntity)
    suspend fun deleteMessage(messageId: Long)
    suspend fun clearChat(chatId: Long)
    suspend fun getAnswer(systemRole: String, query: String): Result<AIResponse>

    suspend fun getChatSettings(chatId: Long): Result<Chat>
    // написать метод для получения чата,
    //модельку чата надо запихнуть в отдельный модуль и шарить с тем кому он еще нужен
    // и вообще все модельки так сделать
}