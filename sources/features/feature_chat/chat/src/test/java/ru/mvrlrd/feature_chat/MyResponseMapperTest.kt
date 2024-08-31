package ru.mvrlrd.feature_chat

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import ru.mvrlrd.feature_chat.data.MyResponseMapper

class MyResponseMapperTest {
    private lateinit var mapper: MyResponseMapper

    @Before
    fun setup() {
        mapper = MyResponseMapper()
    }

    @Test
    fun `mapping message to messageDto`() {
        val expectedMessageDto = HomeTestUnitFactory.getMessageDto()
        val message = HomeTestUnitFactory.getMessage()
        val actualMessageDto = mapper.mapMessageToMessageDto(message)
        assertEquals(expectedMessageDto, actualMessageDto)
    }


    @Test
    fun `mapping CompletionOptions To CompletionOptionsDto`() {
        val expectedCompletionOptionsDto = HomeTestUnitFactory.getCompletionOptionsDto()
        val completionOptions = HomeTestUnitFactory.getCompletionOptions()
        val actualCompletionOptionsDto =
            mapper.mapCompletionOptionsToCompletionOptionsDto(completionOptions)
        assertEquals(
            "wrong mapping completionOptions to completionOptionsDto",
            expectedCompletionOptionsDto,
            actualCompletionOptionsDto
        )
    }

    @Test
    fun `mapping AiRequest To RequestDataDto`() {
        val expectedRequestDataDto = HomeTestUnitFactory.getRequestDataDto()
        val actualRequestDataDto =
            mapper.mapAiRequestToRequestDataDto(HomeTestUnitFactory.getAiRequest())
        assertEquals(expectedRequestDataDto, actualRequestDataDto)
    }

    @Test
    fun `mapping ServerResponse to AiResponse`() {
        val serverResponse = HomeTestUnitFactory.getGoodServerResponse()
        val expectedAIResponse = HomeTestUnitFactory.getAIResponse()
        val actualAiResponse = mapper.mapServerResponseToAIResponse(serverResponse)
            .copy(date = expectedAIResponse.date)
        assertEquals(
            "wrong mapping serverResponse to AiResponse",
            expectedAIResponse,
            actualAiResponse
        )
    }

    @Test
    fun `mapping from ServerResponse to AiResponse with wrong fields`() {
        val serverResponse = HomeTestUnitFactory.getBadServerResponse()
        val expectedAIResponse = HomeTestUnitFactory.getAIResponse().copy(
            text = "",
            inputTextTokens = -1,
            completionTokens = -1,
            totalTokens = -1,
            modelVersion = "ver. 100"
        )
        val actualAiResponse = mapper.mapServerResponseToAIResponse(serverResponse)
            .copy(date = expectedAIResponse.date)
        assertEquals(
            "wrong mapping serverResponse to AiResponse when fields are wrong",
            expectedAIResponse,
            actualAiResponse
        )
    }
}