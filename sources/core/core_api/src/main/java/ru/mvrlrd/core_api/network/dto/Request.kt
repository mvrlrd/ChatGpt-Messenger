package ru.mvrlrd.core_api.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


sealed interface Request{

}

@Serializable
@SerialName("CompletionOptions")
data class CompletionOptionsDto(
    @SerialName("stream")
    val stream: Boolean,
    @SerialName("temperature")
    val temperature: Double,
    @SerialName("maxTokens")
    val maxTokens: String
)

@Serializable
@SerialName("Message")
data class MessageDto(
    @SerialName("role")
    val role: String,
    @SerialName("text")
    val text: String
)

@Serializable
@SerialName("RequestData")
data class RequestDataDto(
    @SerialName("modelUri")
    val modelUri: String="",
    @SerialName("completionOptions")
    val completionOptionsDto: CompletionOptionsDto,
    @SerialName("messages")
    val messageDtos: List<MessageDto>
) : Request
data object BadRequest: Request