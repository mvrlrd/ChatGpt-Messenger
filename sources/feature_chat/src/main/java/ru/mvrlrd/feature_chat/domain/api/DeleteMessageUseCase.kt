package ru.mvrlrd.feature_chat.domain.api

interface DeleteMessageUseCase {
    suspend operator fun invoke(messageId: Long)
}