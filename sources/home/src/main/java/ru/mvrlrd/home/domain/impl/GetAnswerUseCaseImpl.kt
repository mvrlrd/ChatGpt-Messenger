package ru.mvrlrd.home.domain.impl

import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.GetAnswerUseCase
import javax.inject.Inject

class GetAnswerUseCaseImpl @Inject constructor(private val repository: Repository): GetAnswerUseCase {
    override suspend operator fun invoke(systemRole: String, query: String): Result<MyResponse> {
        val mockRespond = ServerResponse.getDefault("_Здрав.sadasdasdasdadasdasdasdadapsfjsdhvksdhvsdhvlihsdvhksdhvksdkvhs sdhfksdh fksdhk fhsdkfh ksidhflisdhlf isjf flsd hlsh ilfs lfhsh dlfsldf lsdfi sdlfs dfls djflsjd lfjsdli fjslifj jlsdflsdif")
        return  Result.success(mockRespond)
        return repository.getAnswer(systemRole, query)
    }
}