package ru.mvrlrd.core_api.network

import ru.mvrlrd.core_api.network.dto.MyResponse
import ru.mvrlrd.core_api.network.dto.Request


interface RemoteRepository {
    suspend fun getAnswer(request: Request): Result<MyResponse>
}