package ru.mvrlrd.core_api.network

import ru.mvrlrd.core_api.dto.Response
import ru.mvrlrd.core_api.dto.Request

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}