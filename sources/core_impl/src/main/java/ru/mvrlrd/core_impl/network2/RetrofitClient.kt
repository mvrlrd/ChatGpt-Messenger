package ru.mvrlrd.core_impl.network2

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestData
import ru.mvrlrd.core_api.network.dto.Response
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.core_impl.BuildConfig
import javax.inject.Inject

class RetrofitClient @Inject constructor(private val apiService: ApiService): NetworkClient {

    override suspend fun doRequest(request: Request): Response {
        when (request){
            is RequestData -> {
                val jsonString = Json.encodeToString<RequestData>(request)
                val response = apiService.getCompletion(jsonString.toRequestBody())
                Log.d("TAG", "_____ ${response.body()}")
//               ( response as ServerResponse).result.alternatives.forEach {
//                   Log.d("TAG", "_____ ${it.message}")
//               }
//удалить интерфейс response?
                //обработка ошибок
                return ServerResponse.getDefault()
            }
        }
        return ServerResponse.getDefault()
    }
}