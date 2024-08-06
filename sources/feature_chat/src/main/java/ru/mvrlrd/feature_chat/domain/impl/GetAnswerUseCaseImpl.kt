package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.feature_chat.domain.AIResponse
import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import javax.inject.Inject

@Open
class GetAnswerUseCaseImpl @Inject constructor(private val repository: Repository): GetAnswerUseCase {
    override suspend operator fun invoke(systemRole: String, query: String): Result<AIResponse> {
        return repository.getAnswer(systemRole, query)
    }
}