package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import javax.inject.Inject

class DeleteMessageUseCaseImpl@Inject constructor(private val repository: Repository): DeleteMessageUseCase {
    override suspend fun invoke(messageId: Long) {
        repository.deleteMessage(messageId)
    }
}