package ru.mvrlrd.home.domain.impl

import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.DeleteMessageUseCase
import javax.inject.Inject

class DeleteMessageUseCaseImpl@Inject constructor(private val repository: Repository): DeleteMessageUseCase {
    override suspend fun invoke(messageId: Long) {
        repository.deleteMessage(messageId)
    }
}