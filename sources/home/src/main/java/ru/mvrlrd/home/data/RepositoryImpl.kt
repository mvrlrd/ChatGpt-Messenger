package ru.mvrlrd.home.data

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.home.domain.AIResponse
import ru.mvrlrd.home.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ChatDao,
    private val remoteRepository: RemoteRepository,
    private val mapper: MyResponseMapper
) : Repository {
    override fun getAllMessages(chatId: Long): Flow<List<Message>> {
        return dao.getMessagesForChat(chatId)
    }

    override suspend fun saveMessage(message: Message) {
        dao.insertMessage(message)
    }

    override suspend fun deleteMessage(messageId: Long) {
        dao.deleteMessage(messageId)
    }

    override suspend fun clearChat(chatId: Long) {
        dao.clearMessages(chatId)
    }

    override suspend fun getAnswer(systemRole: String, query: String): Result<AIResponse> {
        remoteRepository.getAnswer(systemRole, query).onSuccess {
            val aiResponse = mapper.mapMyResponseToAIResponse(it as ServerResponse)
            return Result.success(aiResponse)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(IllegalArgumentException())
    }
}