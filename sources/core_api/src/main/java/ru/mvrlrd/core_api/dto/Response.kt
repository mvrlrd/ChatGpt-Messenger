package ru.mvrlrd.core_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
interface Response

@Serializable
data class Alternative(
    val message: Message,
    val status: String
)

@Serializable
data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage,
    @SerialName("modelVersion")
    val modelVersion: String
)

@Serializable
data class Usage(
    @SerialName("inputTextTokens")
    val inputTextTokens: String,
    @SerialName("completionTokens")
    val completionTokens: String,
    @SerialName("totalTokens")
    val totalTokens: String
)

@Serializable
data class ServerResponse(
    val result: Result
): Response{
    companion object{
        fun getDefault() =
            ServerResponse(
                Result(listOf(Alternative(Message("","error"), "")), Usage("","",""), "")
            )
    }
}