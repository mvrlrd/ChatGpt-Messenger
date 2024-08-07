package ru.mvrlrd.feature_chat.data

import ru.mvrlrd.core_api.network.dto.ServerResponseDto
import ru.mvrlrd.feature_chat.domain.AIResponse

import javax.inject.Inject

class MyResponseMapper @Inject constructor() {
    fun mapMyResponseToAIResponse(serverResponseDto: ServerResponseDto): AIResponse{
        return with(serverResponseDto){
            AIResponse(
                answer = serverResponseDto.resultDto.alternativeDtos.firstOrNull()?.messageDto?.text?:"no ideas...",
                role = serverResponseDto.resultDto.alternativeDtos.firstOrNull()?.messageDto?.role?:"no role...",
                inputTextTokens = resultDto.usageDto.inputTextTokens.toIntOrNull()?:-1,
                completionTokens = resultDto.usageDto.completionTokens.toIntOrNull()?:-1,
                totalTokens = resultDto.usageDto.totalTokens.toIntOrNull()?:-1,
                modelVersion = resultDto.modelVersion?:"___",
            )
        }
    }
}