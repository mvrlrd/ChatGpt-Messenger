package ru.mvrlrd.feature_chat.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.ChatMapper
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

    override suspend fun getAnswer(aiRequest: AiRequest): Result<AIResponse> {
//        val request = RequestDataDto.getDefault(
//          listOfMessageDtos =   listOf(
//                MessageDto(role = "system", text = (systemRole.ifBlank { "ты умный ассистент" })),
//              MessageDto("user", query)
//            )
//        )
        Log.d("TAG", "___ChatRepositoryImpl airequest = ${aiRequest}")
        val requestData = mapper.mapAiRequestToRequestDataDto(aiRequest)
        remoteRepository.getAnswer(requestData).onSuccess {
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
}