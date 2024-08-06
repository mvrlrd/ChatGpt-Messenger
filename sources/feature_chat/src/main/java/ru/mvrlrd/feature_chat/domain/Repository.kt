package ru.mvrlrd.feature_chat.domain

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Message

interface Repository {
    fun getAllMessages(chatId: Long): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
    suspend fun deleteMessage(messageId: Long)
    suspend fun clearChat(chatId: Long)
    suspend fun getAnswer(systemRole: String, query: String): Result<AIResponse>
    // написать метод для получения чата,
    //модельку чата надо запихнуть в отдельный модуль и шарить с тем кому он еще нужен
    // и вообще все модельки так сделать
}