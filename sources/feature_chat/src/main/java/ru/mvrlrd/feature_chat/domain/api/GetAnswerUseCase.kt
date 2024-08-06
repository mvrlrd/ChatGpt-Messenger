package ru.mvrlrd.feature_chat.domain.api

import ru.mvrlrd.feature_chat.domain.AIResponse

interface GetAnswerUseCase {
    suspend operator fun invoke(systemRole: String ="", query: String): Result<AIResponse>
}