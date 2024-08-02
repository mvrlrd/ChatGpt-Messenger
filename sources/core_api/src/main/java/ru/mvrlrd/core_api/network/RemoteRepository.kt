package ru.mvrlrd.core_api.network

import ru.mvrlrd.core_api.network.dto.MyResponse


interface RemoteRepository {
    suspend fun getAnswer(systemRole: String,query: String): Result<MyResponse>
}