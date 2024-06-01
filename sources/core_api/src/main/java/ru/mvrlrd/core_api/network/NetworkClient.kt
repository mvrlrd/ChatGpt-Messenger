package ru.mvrlrd.core_api.network

import ru.mvrlrd.core_api.network.dto.Response
import ru.mvrlrd.core_api.network.dto.Request

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}