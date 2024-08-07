package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.feature_chat.domain.AIResponse
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import javax.inject.Inject

@Open
class GetAnswerUseCaseImpl @Inject constructor(private val chatRepository: ChatRepository): GetAnswerUseCase {
    override suspend operator fun invoke(systemRole: String, query: String): Result<AIResponse> {
        return chatRepository.getAnswer(systemRole, query)
    }
}