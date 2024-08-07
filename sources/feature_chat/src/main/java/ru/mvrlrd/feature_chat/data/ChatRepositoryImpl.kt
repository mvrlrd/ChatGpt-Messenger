package ru.mvrlrd.feature_chat.data

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.ChatMapper
import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.MessageDto
import ru.mvrlrd.core_api.network.dto.RequestDataDto
import ru.mvrlrd.core_api.network.dto.ServerResponseDto
import ru.mvrlrd.feature_chat.domain.AIResponse
import ru.mvrlrd.feature_chat.domain.ChatRepository
import javax.inject.Inject

@Open
class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatDao,
    private val remoteRepository: RemoteRepository,
    private val mapper: MyResponseMapper,
    private val chatMapper: ChatMapper
) : ChatRepository {

    override fun getAllMessages(chatId: Long): Flow<List<MessageEntity>> {
        return dao.getMessagesForChat(chatId)
    }

    override suspend fun saveMessage(messageEntity: MessageEntity) {
        dao.insertMessage(messageEntity)
    }

    override suspend fun deleteMessage(messageId: Long) {
        dao.deleteMessage(messageId)
    }

    override suspend fun clearChat(chatId: Long) {
        dao.clearMessages(chatId)
    }

    override suspend fun getAnswer(systemRole: String, query: String): Result<AIResponse> {
        val request = RequestDataDto.getDefault(
          listOfMessageDtos =   listOf(
                MessageDto(
                    role = "system",
                    text = (systemRole.ifBlank { "ты умный ассистен" })
                ), MessageDto("user", query)
            )
        )
        remoteRepository.getAnswer(request).onSuccess {
            val aiResponse = mapper.mapMyResponseToAIResponse(it as ServerResponseDto)
            return Result.success(aiResponse)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(IllegalArgumentException())
    }

    override suspend fun getChatSettings(chatId: Long): Result<Chat> {
        val chatEntity = dao.getChat(chatId)
        val chat = chatMapper.mapChatEntityToChat(chatEntity)
        return Result.success(chat)
    }
}