package ru.mvrlrd.core_impl.network

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestData
import ru.mvrlrd.core_api.network.dto.Response
import ru.mvrlrd.core_api.network.dto.ServerResponse
import javax.inject.Inject

 const val API_KEY = "AQVN2zyLDOY0ImCZQEi2zoIpClm5tF3onFZiOgRg"
 const val URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
class KtorClient @Inject constructor(private val client: HttpClient): NetworkClient {

    override suspend fun doRequest(request: Request): Response {
       when (request){
           is RequestData -> {
               val response = client.post(URL) {
                   val jsonString = Json.encodeToString<RequestData>(request)
                   headers {
                       append("Authorization", "Api-Key $API_KEY")
                   }
                   setBody(jsonString)
               }
               if (response.status != HttpStatusCode.OK) {
                   throw RuntimeException("Invalid response received: code: ${response.status.value}, message: ${response.bodyAsText()}")
               }
               return Json.decodeFromString<ServerResponse>(response.bodyAsText())
           }
       }
        return ServerResponse.getDefault()
    }
}