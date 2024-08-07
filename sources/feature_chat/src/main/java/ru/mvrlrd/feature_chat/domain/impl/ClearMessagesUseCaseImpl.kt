package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import javax.inject.Inject

class ClearMessagesUseCaseImpl@Inject constructor(private val chatRepository: ChatRepository): ClearMessagesUseCase {
    override suspend fun invoke(chatId: Long) {
        chatRepository.clearChat(chatId)
    }
}