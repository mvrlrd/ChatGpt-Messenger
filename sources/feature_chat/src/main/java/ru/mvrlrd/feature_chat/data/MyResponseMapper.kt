package ru.mvrlrd.feature_chat.data

import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.feature_chat.domain.AIResponse

import javax.inject.Inject

class MyResponseMapper @Inject constructor() {
    fun mapMyResponseToAIResponse(serverResponse: ServerResponse): AIResponse{
        return with(serverResponse){
            AIResponse(
                answer = serverResponse.result.alternatives.firstOrNull()?.message?.text?:"no ideas...",
                role = serverResponse.result.alternatives.firstOrNull()?.message?.role?:"no role...",
                inputTextTokens = result.usage.inputTextTokens.toIntOrNull()?:-1,
                completionTokens = result.usage.completionTokens.toIntOrNull()?:-1,
                totalTokens = result.usage.totalTokens.toIntOrNull()?:-1,
                modelVersion = result.modelVersion?:"___",
            )
        }
    }
}