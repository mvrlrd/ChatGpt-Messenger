package ru.mvrlrd.ktor_module


import dto.Response
import ru.mvrlrd.core_api.NetworkClient
import ru.mvrlrd.core_api.dto.TextResponse

class RemoteRepository(private val client: NetworkClient) {
    suspend fun getAnswer(query: String): String{
        return (client.doRequest(query) as TextResponse).text
    }

}