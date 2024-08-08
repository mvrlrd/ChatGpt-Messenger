package ru.mvrlrd.core_api.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface MyResponse

@Serializable
@SerialName("Alternative")
data class AlternativeDto(
    @SerialName("message")
    val messageDto: MessageDto,
    @SerialName("status")
    val status: String
)

@Serializable
@SerialName("Result")
data class ResultDto(
    @SerialName("alternatives")
    val alternativeDtos: List<AlternativeDto>,
    @SerialName("usage")
    val usageDto: UsageDto,
    @SerialName("modelVersion")
    val modelVersion: String
)

@Serializable
data class UsageDto(
    @SerialName("inputTextTokens")
    val inputTextTokens: String,
    @SerialName("completionTokens")
    val completionTokens: String,
    @SerialName("totalTokens")
    val totalTokens: String
)

@Serializable
@SerialName("ServerResponse")
data class ServerResponseDto(
    @SerialName("result")
    val resultDto: ResultDto
): MyResponse {
    companion object{
        fun getDefault(text: String="") =
            ServerResponseDto(
                ResultDto(listOf(AlternativeDto(MessageDto("",text), "")), UsageDto("","",""), "")
            )
    }
}