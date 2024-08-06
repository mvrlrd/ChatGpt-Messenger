package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import javax.inject.Inject

class ClearMessagesUseCaseImpl@Inject constructor(private val repository: Repository): ClearMessagesUseCase {
    override suspend fun invoke(chatId: Long) {
        repository.clearChat(chatId)
    }
}