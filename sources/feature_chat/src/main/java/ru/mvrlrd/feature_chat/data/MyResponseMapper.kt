package ru.mvrlrd.feature_chat.data

import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.core_api.network.dto.CompletionOptionsDto
import ru.mvrlrd.core_api.network.dto.MessageDto
import ru.mvrlrd.core_api.network.dto.RequestDataDto
import ru.mvrlrd.core_api.network.dto.ServerResponseDto
import ru.mvrlrd.feature_chat.domain.model.AIResponse
import ru.mvrlrd.feature_chat.domain.model.AiRequest
import ru.mvrlrd.feature_chat.domain.model.Message

import javax.inject.Inject

class MyResponseMapper @Inject constructor() {
    fun mapMyResponseToAIResponse(serverResponseDto: ServerResponseDto): AIResponse {
        return with(serverResponseDto){
            AIResponse(
                text = serverResponseDto.resultDto.alternativeDtos.firstOrNull()?.messageDto?.text?:"no ideas...",
                role = serverResponseDto.resultDto.alternativeDtos.firstOrNull()?.messageDto?.role?:"no role...",
                inputTextTokens = resultDto.usageDto.inputTextTokens.toIntOrNull()?:-1,
                completionTokens = resultDto.usageDto.completionTokens.toIntOrNull()?:-1,
                totalTokens = resultDto.usageDto.totalTokens.toIntOrNull()?:-1,
                modelVersion = resultDto.modelVersion?:"___",
            )
        }
    }
    fun mapAiRequestToRequestData(aiRequest: AiRequest): RequestDataDto{
       return with(aiRequest){
            RequestDataDto(
                completionOptionsDto = mapCompletionOptionsToCompletionOptionsDto(completionOptions),
                messageDtos =  messages.map { mapMessageToMessageDto(it) }
            )
        }
    }
    private fun mapMessageToMessageDto(message: Message): MessageDto{
       return with(message){
            MessageDto(
                role=role,
                text=text
            )
        }
    }


    private fun mapCompletionOptionsToCompletionOptionsDto(completionOptions: CompletionOptions): CompletionOptionsDto{
        return with(completionOptions){
            CompletionOptionsDto(
                stream = stream,
                temperature=temperature,
                maxTokens = maxTokens.toString()
            )
        }
    }
}
