package ru.mvrlrd.core_impl.network

import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.Request
import ru.mvrlrd.core_api.network.dto.RequestDataDto
import ru.mvrlrd.core_api.network.dto.ServerResponseDto
import javax.inject.Inject

class RetrofitClient @Inject constructor(private val apiService: ApiService, private val internetController: InternetController): NetworkClient {


    override suspend fun doRequest(request: Request): Result<MyResponse> {
        return when (request) {
            is RequestDataDto -> getAnswer(request)
            else -> Result.failure(MyException.BadRequest)
        }
    }

    private suspend fun getAnswer(requestDataDto: RequestDataDto): Result<MyResponse> {
        return requestWithResponseHandling(ServerResponseDto.getDefault()) {
            val jsonString = Json.encodeToString(requestDataDto)
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
                Log.e("TAG","RetrofitClient success: ${response.body()}")
                Result.success(response.body() ?: default)
            }
            false -> {
                if (isConnected) {
                    Log.e("TAG","RetrofitClient fail: ${response.body()}")
                    when (response.code()){
                        400 -> Result.failure(MyException.BadRequest)
                        401 -> Result.failure(MyException.Unauthorized)
                        404 -> Result.failure(MyException.NotFound)
                        else -> {

                            Result.failure(MyException.UnknownException)
                        }
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
                    responseHandle(request(), default, internetController.isInternetAvailable())
                } catch (exception: Throwable) {
                    Log.e("TAG","RetrofitClient requestWithResponseHandling(): exception=$exception,    message=${exception.message}")
                    Result.failure(MyException.UnknownException)
                }
            }
            false -> {
                Result.failure(MyException.Disconnected)
            }
        }
    }
}