package ru.mvrlrd.core_api

import dto.Request
import dto.Response

interface NetworkClient {
    suspend fun doRequest(query: String): Response
}