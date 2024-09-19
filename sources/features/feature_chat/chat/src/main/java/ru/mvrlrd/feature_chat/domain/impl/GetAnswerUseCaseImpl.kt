package ru.mvrlrd.feature_chat.domain.impl

import android.util.Log
import ru.mvrlrd.base_chat_home.model.Message
import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.feature_chat.domain.model.AIResponse
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.model.AiRequest
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Open
class GetAnswerUseCaseImpl @Inject constructor(private val chatRepository: ChatRepository) :
    GetAnswerUseCase {
    override suspend operator fun invoke(chatd: Long, query: String): Result<AIResponse> {
        chatRepository.getChatSettings(chatId = chatd).onSuccess {
            val completionOptions = it.completionOptions
            val setupMessage = Message(
                role = SYSTEM,
                text = it.roleText
            )
            val queryMessage = Message(
                role = USER,
                text = query
            )
            val messages = listOf(setupMessage, queryMessage)
            val aiRequest = AiRequest(
                completionOptions = completionOptions,
                messages = messages
            )
            return chatRepository.getAnswer(aiRequest, it.chatId, it.prompt)
        }.onFailure {
            Log.e("TAG", "GetAnswerUseCaseImpl: failure: ${it.message}")
            return Result.failure(IllegalArgumentException("bad request"))
        }
        Log.e("TAG", "GetAnswerUseCaseImpl: WTF")
        return Result.failure(IllegalArgumentException("bad request"))
    }
    companion object{
        private const val SYSTEM = "system"
        private const val USER = "user"
    }
}