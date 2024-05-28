package ru.mvrlrd.ktor_module

import kotlinx.serialization.json.Json
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.dto.Message
import ru.mvrlrd.core_api.dto.RequestData
import ru.mvrlrd.core_api.dto.ServerResponse
import ru.mvrlrd.core_api.dto.TextResponse
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor(private val client: NetworkClient) :
    RemoteRepository {
    override suspend fun getAnswer(systemRole: String, query: String): String {
        val request = RequestData.getDefault(
            listOf(
                Message(
                    "system",
                    (systemRole.ifBlank { "ты умный ассистент" })
                ), Message("user", query)
            )
        )

        return (client.doRequest(request) as ServerResponse).result.alternatives[0].message.text
    }

}