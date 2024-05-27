package ru.mvrlrd.ktor_module

import dto.Request
import dto.Response
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.mvrlrd.core_api.NetworkClient
import ru.mvrlrd.core_api.dto.TextResponse

class KtorClient(): NetworkClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

    }
    override suspend fun doRequest(query: String): Response {
       val request = KtorRequest(query)

       val response =  client.post(URL) {
            headers {
                addHeaders().forEach{ (key, value) ->
                    append(key, value)
                }
            }
            setBody(request.request)
        }
        if (response.status != HttpStatusCode.OK) {
            throw RuntimeException("Invalid response received: code: ${response.status.value}, message: ${response.bodyAsText()}")
        }
        return TextResponse(response.bodyAsText())
    }
private fun addHeaders():Map<String, String>{
    return when {
        API_KEY != null -> mapOf("Authorization" to "Api-Key $API_KEY")
        else -> {
            println("Please save either an IAM token or an API key into a corresponding `IAM_TOKEN` or `API_KEY` environment variable.")
            throw RuntimeException("No Api_Key")
        }
    }
}

}

private const val API_KEY = "AQVN2zyLDOY0ImCZQEi2zoIpClm5tF3onFZiOgRg"
private const val URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"