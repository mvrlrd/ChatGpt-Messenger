package ru.mvrlrd.feature_chat.domain.api

import ru.mvrlrd.feature_chat.domain.model.AIResponse
import ru.mvrlrd.feature_chat.domain.model.AiRequest

interface GetAnswerUseCase {
    suspend operator fun invoke(chatId: Long, query: String): Result<AIResponse>
}