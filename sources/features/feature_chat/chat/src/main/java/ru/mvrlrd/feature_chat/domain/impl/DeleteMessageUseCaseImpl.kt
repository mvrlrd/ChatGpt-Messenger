package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import javax.inject.Inject

class DeleteMessageUseCaseImpl@Inject constructor(private val chatRepository: ChatRepository): DeleteMessageUseCase {
    override suspend fun invoke(messageId: Long) {
        chatRepository.deleteMessage(messageId)
    }
}