package ru.mvrlrd.feature_chat.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.ChatMapper
import ru.mvrlrd.base_chat_home.model.Message
import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.ServerResponseDto
import ru.mvrlrd.feature_chat.domain.model.AIResponse
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.model.AiRequest
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

    override suspend fun getAnswer(
        aiRequest: AiRequest,
        chatId: Long,
        prompt: Boolean
    ): Result<AIResponse> {
        val request = if (prompt) {
            aiRequest
        } else {
            val scope = CoroutineScope(Dispatchers.IO).async {
                dao.getContextMessages(chatId)
            }
            val contextMessages = scope.await().map {
                Message(
                    role = if (it.isReceived) ASSISTANT else USER,
                    text = it.text
                )
            }
            val chatContext = mutableListOf(aiRequest.messages[0])
            chatContext.addAll(contextMessages)
            aiRequest.copy(messages = chatContext)
        }
        val requestDataDto = mapper.mapAiRequestToRequestDataDto(request)
        remoteRepository.getAnswer(requestDataDto).onSuccess {
            val aiResponse = mapper.mapServerResponseToAIResponse(it as ServerResponseDto)
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
    companion object{
        private const val ASSISTANT = "assistant"
        private const val USER = "user"
    }
}