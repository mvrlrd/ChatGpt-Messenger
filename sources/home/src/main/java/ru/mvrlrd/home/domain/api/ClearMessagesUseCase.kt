package ru.mvrlrd.home.domain.api

interface ClearMessagesUseCase {
    suspend operator fun invoke(chatId: Long)
}