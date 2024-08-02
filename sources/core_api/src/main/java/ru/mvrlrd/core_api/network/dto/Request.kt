package ru.mvrlrd.core_api.network.dto

import kotlinx.serialization.Serializable
import javax.inject.Named


sealed interface Request{

}

@Serializable
data class CompletionOptions(
    val stream: Boolean,
    val temperature: Double,
    val maxTokens: String
) {
    companion object {
        fun getDefault() = CompletionOptions(
            false,
            0.6,
            "2000"
        )
    }
}

@Serializable
data class Message(
    val role: String,
    val text: String
)

@Serializable
data class RequestData(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>
) : Request {
    companion object {
        fun getDefault(@Named("modelUrl") modelUri: String, listOfMessages: List<Message>): RequestData =
            RequestData(
                modelUri = modelUri,
                completionOptions = CompletionOptions.getDefault(),
                messages = listOfMessages
            )
    }
}