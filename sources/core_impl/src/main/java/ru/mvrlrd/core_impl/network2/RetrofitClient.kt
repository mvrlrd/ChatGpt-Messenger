package ru.mvrlrd.core_impl.network2

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestData
import ru.mvrlrd.core_impl.network.MyException
import javax.inject.Inject

class RetrofitClient @Inject constructor(private val apiService: ApiService): NetworkClient {

    override suspend fun doRequest(request: Request): Result<MyResponse> {
        return try {
            when (request) {
                is RequestData -> {
                    val jsonString = Json.encodeToString<RequestData>(request)
                    val response = apiService.getCompletion(jsonString.toRequestBody())
                    if (response.isSuccessful) {
                        Result.success(response.body()!!)
//                        Result.failure(MyException.BadRequest)
                    } else {
                        // Обработка HTTP ошибок (например, 400, 500)
                        Result.failure(MyException.HttpError(response.code(), response.message()))
                    }
                }

                else -> Result.failure(MyException.BadRequest)
            }
        } catch (e: Exception) {
            // Обработка исключений, связанных с сетью, JSON, и т.д.
            Result.failure(MyException.NetworkError(e))
        }
    }
}