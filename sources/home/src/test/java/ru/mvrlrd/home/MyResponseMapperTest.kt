package ru.mvrlrd.home

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.mvrlrd.home.data.MyResponseMapper


class MyResponseMapperTest {
    private lateinit var mapper: MyResponseMapper

    @Before
    fun setup(){
        mapper = MyResponseMapper()
    }

    @Test
    fun `mapping from ServerResponce to AiResponce`(){
        val serverResponse = HomeTestUnitFactory.getGoodServerResponse()
        val expectedAIResponse = HomeTestUnitFactory.getAIResponse()

        val actualAiResponse = mapper.mapMyResponseToAIResponse(serverResponse).copy(date = 1000L)
        assertEquals("wrong mapping serverResponse to AiResponse", expectedAIResponse, actualAiResponse)
    }

    @Test
    fun `mapping from ServerResponce to AiResponce with wrong fields`() {
        val serverResponse = HomeTestUnitFactory.getBadServerResponse()
        val expectedAIResponse = HomeTestUnitFactory.getAIResponse().copy(
            answer = "",
            inputTextTokens = -1,
            completionTokens = -1,
            totalTokens = -1,
            modelVersion = "ver. 100"
        )
        val actualAiResponse = mapper.mapMyResponseToAIResponse(serverResponse).copy(date = 1000L)
        assertEquals(
            "wrong mapping serverResponse to AiResponse when fields are wrong",
            expectedAIResponse,
            actualAiResponse.copy(date = 1000L)
        )
    }
}