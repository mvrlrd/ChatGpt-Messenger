package ru.mvrlrd.feature_chat.domain.api

interface ClearMessagesUseCase {
    suspend operator fun invoke(chatId: Long)
}