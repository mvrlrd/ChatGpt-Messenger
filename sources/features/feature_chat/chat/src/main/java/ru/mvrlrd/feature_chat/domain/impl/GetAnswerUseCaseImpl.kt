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
            Log.d("TAG","___GetAnswerUseCaseImpl chat = ${it}  roleText = ${it.roleText}")
            val completionOptions = it.completionOptions
            val setupMessage = Message(
                role = "system",
                text = it.roleText
            )
            val queryMessage = Message(
                role = "user",
                text = query
            )
            val messages = listOf(setupMessage, queryMessage)
            val aiRequest = AiRequest(
                completionOptions = completionOptions,
                messages = messages
            )
            Log.d("TAG","___GetAnswerUseCaseImpl ${aiRequest}")
            return chatRepository.getAnswer(aiRequest)
        }.onFailure {
            return Result.failure(IllegalArgumentException("bad request"))
        }
        return Result.failure(IllegalArgumentException("bad request"))
    }
}