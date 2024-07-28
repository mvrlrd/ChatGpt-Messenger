package ru.mvrlrd.home.domain.impl

import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.ClearMessagesUseCase
import javax.inject.Inject

class ClearMessagesUseCaseImpl@Inject constructor(private val repository: Repository): ClearMessagesUseCase {
    override suspend fun invoke(chatId: Long) {
        repository.clearChat(chatId)
    }
}