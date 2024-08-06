package ru.mvrlrd.home

import ru.mvrlrd.core_api.network.dto.Alternative
import ru.mvrlrd.core_api.network.dto.Message
import ru.mvrlrd.core_api.network.dto.Result
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.core_api.network.dto.Usage
import ru.mvrlrd.feature_chat.data.MyResponseMapper
import ru.mvrlrd.feature_chat.domain.AIResponse

object HomeTestUnitFactory {
    fun getMapper() = MyResponseMapper()

    fun getAIResponse() = AIResponse(
        "ai response",
        "assistant",
        100,
        50,
        150,
        "1.0",
        1000L
    )

    private var serverResponseFactory: ServerResponseFactory = GoodResponseFactory()

    fun getBadServerResponse(): ServerResponse {
        serverResponseFactory = BadResponseFactory()
        return serverResponseFactory.createResponse()
    }

    fun getGoodServerResponse(): ServerResponse {
        serverResponseFactory = GoodResponseFactory()
        return serverResponseFactory.createResponse()
    }



}
abstract class ServerResponseFactory(){
    abstract fun createResponse(): ServerResponse
    abstract fun createResult(): Result
    abstract fun createUsage():Usage
    abstract fun createAlternatives(): Alternative
    abstract fun createMessage(): Message
}
class BadResponseFactory(): ServerResponseFactory(){
    override fun createResponse()=ServerResponse(
        createResult()
    )

    override fun createResult()= Result(
        alternatives = listOf(createAlternatives()),
        usage = createUsage(),
        modelVersion = "ver. 100"
    )

    override fun createUsage()= Usage(
        inputTextTokens = "one",
        completionTokens = "one",
        totalTokens = "two"
    )

    override fun createAlternatives() =
        Alternative(
            message = createMessage(),
            status = "10",
        )

    override fun createMessage() = Message(
        role = "assistant",
        text = ""
    )
}
class GoodResponseFactory(): ServerResponseFactory(){
    override fun createResponse()=ServerResponse(
        createResult()
    )

    override fun createResult()= Result(
        alternatives = listOf(createAlternatives()),
        usage = createUsage(),
        modelVersion = "1.0"
    )

    override fun createUsage()= Usage(
        inputTextTokens = "100",
        completionTokens = "50",
        totalTokens = "150"
    )

    override fun createAlternatives()=
        Alternative(
    message = createMessage(),
    status = "ALTERNATIVE_STATUS_FINAL",
    )

    override fun createMessage() = Message(
    role = "assistant",
    text = "ai response"
    )
}