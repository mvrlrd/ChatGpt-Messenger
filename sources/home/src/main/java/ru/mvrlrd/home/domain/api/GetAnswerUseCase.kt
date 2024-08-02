package ru.mvrlrd.home.domain.api

import ru.mvrlrd.core_api.network.dto.MyResponse

interface GetAnswerUseCase {
    suspend operator fun invoke(systemRole: String, query: String): Result<MyResponse>
}