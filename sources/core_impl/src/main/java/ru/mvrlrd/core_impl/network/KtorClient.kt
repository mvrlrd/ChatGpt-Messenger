package ru.mvrlrd.core_impl.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Response
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestData
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.core_impl.BuildConfig
import javax.inject.Inject


class KtorClient @Inject constructor(private val client: HttpClient): NetworkClient {

    override suspend fun doRequest(request: Request): Result<MyResponse> {
       when (request){
           is RequestData -> {
               val response = client.post(BuildConfig.BASE_URL) {
                   val jsonString = Json.encodeToString<RequestData>(request)
                   headers {
                       append("Authorization", "Api-Key ${BuildConfig.API_KEY}")
                   }
                   setBody(jsonString)
               }
               if (response.status != HttpStatusCode.OK) {
                   throw RuntimeException("Invalid response received: code: ${response.status.value}, message: ${response.bodyAsText()}")
               }
               return Result.success(response.body())
//               Json.decodeFromString<ServerResponse>(response.bodyAsText())
           }
       }
        return Result.success(ServerResponse.getDefault())
    }
}