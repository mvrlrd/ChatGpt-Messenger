package ru.mvrlrd.home.data

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.home.domain.Repository
import javax.inject.Inject

class RepositoryImpl@Inject constructor(private val dao: ChatDao): Repository {
    override fun getAllMessages(chatId: Long): Flow<List<Message>> {
        return dao.getMessagesForChat(chatId)
    }

    override suspend fun saveMessage(message: Message) {
        dao.insertMessage(message)
    }

    override suspend fun deleteMessage(messageId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun clearChat(chatId: Long) {
        TODO("Not yet implemented")
    }
}