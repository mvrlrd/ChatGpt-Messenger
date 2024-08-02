package ru.mvrlrd.core_impl.network


import android.util.Log
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.Message
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.RequestData
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor(private val client: NetworkClient) :
    RemoteRepository {
    override suspend fun getAnswer(
        systemRole: String,
        query: String
    ): Result<MyResponse> {
        val request = RequestData.getDefault(
            listOf(
                Message(
                    "system",
                    (systemRole.ifBlank { "ты умный ассистент" })
                ), Message("user", query)
            )
        )
        client.doRequest(request = request).runCatching {
            return this
        }.recoverCatching { throwable ->
            return when (throwable) {
                is MyException.BadRequest -> {
                    Result.failure(MyException.BadRequest)
                }

                is MyException.HttpError -> {
                    Result.failure(MyException.HttpError(throwable.code, throwable.text))
                }

                is MyException.NetworkError -> {
                    Result.failure(MyException.NetworkError(throwable.e))
                }

                else -> {
                    Result.failure(MyException.UnknownException)
                }

            }
        }
        return Result.failure(MyException.UnknownException)
    }
}



sealed class MyException: Throwable(){
    data object UnknownException: MyException()
    data object BadRequest: MyException()
    class HttpError(val code: Int, val text: String): MyException()
    class NetworkError(val e: Throwable): MyException()
}
