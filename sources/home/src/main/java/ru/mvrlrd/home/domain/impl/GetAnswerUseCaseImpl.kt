package ru.mvrlrd.home.domain.impl

import ru.mvrlrd.core_api.annotations.Open
import ru.mvrlrd.home.domain.AIResponse
import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.GetAnswerUseCase
import javax.inject.Inject

@Open
class GetAnswerUseCaseImpl @Inject constructor(private val repository: Repository): GetAnswerUseCase {
    override suspend operator fun invoke(systemRole: String, query: String): Result<AIResponse> {
        return repository.getAnswer(systemRole, query)
    }
}