package ru.mvrlrd.core_impl.network

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestData
import ru.mvrlrd.core_api.network.dto.ServerResponse
import javax.inject.Inject

class RetrofitClient @Inject constructor(private val apiService: ApiService, private val internetController: InternetController): NetworkClient {


    override suspend fun doRequest(request: Request): Result<MyResponse> {
        return when (request) {
            is RequestData -> getAnswer(request)
            else -> Result.failure(MyException.BadRequest)
        }
    }

    private suspend fun getAnswer(requestData: RequestData): Result<MyResponse> {
        return requestWithResponseHandling(ServerResponse.getDefault()) {
            val jsonString = Json.encodeToString(requestData)
            val requestBody = jsonString.toRequestBody()
            apiService.getCompletion(requestBody)
        }
    }

    private  fun <T> responseHandle(
        response: Response<T>,
        default: T,
        isConnected: Boolean
    ): Result<T> {
        return when (response.isSuccessful) {
            true -> {
                Result.success(response.body() ?: default)
            }
            false -> {
                if (isConnected) {
                    when (response.code()){
                        400 -> Result.failure(MyException.BadRequest)
                        401 -> Result.failure(MyException.Unauthorized)
                        404 -> Result.failure(MyException.NotFound)
                        else -> Result.failure(MyException.UnknownException)
                    }

                } else {
                    Result.failure(MyException.Disconnected)
                }
            }
        }
    }

    private suspend fun <T> requestWithResponseHandling(default: T, request: suspend () -> Response<T>): Result<T> {
        return when (internetController.isInternetAvailable()) {
            true -> {
                try {
                    responseHandle(request(), default, true)
                } catch (exception: Throwable) {
                    Result.failure(MyException.UnknownException)
                }
            }
            false -> {
                Result.failure(MyException.Disconnected)
            }
        }
    }
}