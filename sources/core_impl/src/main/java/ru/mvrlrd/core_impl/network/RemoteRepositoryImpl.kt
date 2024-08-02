package ru.mvrlrd.core_impl.network


import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.Message
import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.RequestData
import javax.inject.Inject
import javax.inject.Named

class RemoteRepositoryImp @Inject constructor(private val client: NetworkClient, @Named("modelUrl") private val modelUrl: String) :
    RemoteRepository {
    override suspend fun getAnswer(
        systemRole: String,
        query: String
    ): Result<MyResponse> {
        val request = RequestData.getDefault(
            modelUri = modelUrl,
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

                is MyException.Unauthorized -> {
                    Result.failure(MyException.Unauthorized)
                }

                is MyException.NotFound -> {
                    Result.failure(MyException.NotFound)
                }

                is MyException.Disconnected -> {
                    Result.failure(MyException.Disconnected)
                }

                else -> {
                    Result.failure(MyException.UnknownException)
                }
            }
        }
        return Result.failure(MyException.UnknownException)
    }
}



sealed class MyException(message: String): Throwable(message){
    data object Unauthorized: MyException("Unauthorized")
    data object UnknownException: MyException("UnknownException")
    data object BadRequest: MyException("BadRequest")
    data object NotFound: MyException("NotFound")
    data object Disconnected: MyException("Disconnected")
}
