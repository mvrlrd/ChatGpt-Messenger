package ru.mvrlrd.home.domain.api

import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.home.domain.AIResponse

interface GetAnswerUseCase {
    suspend operator fun invoke(systemRole: String ="", query: String): Result<AIResponse>
}