package ru.mvrlrd.ktor_module

import dto.Request

private const val FOLDER_ID = "b1ghr3vks6vu23rdjtn6"
  class KtorRequest(private val query: String): Request {
      val request: String
          get() = "{\"modelUri\":\"gpt://$FOLDER_ID/yandexgpt-lite\",\"completionOptions\":{\"stream\": false,\"temperature\": 0.6,\"maxTokens\": \"2000\"},\"messages\": [{\"role\": \"system\",\"text\": \"Ты знаток футбола\"},{\"role\":\"user\",\"text\":\"$query\"}]}"

  }