package ru.mvrlrd.home.domain.impl

import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.SaveMessageToChatUseCase
import javax.inject.Inject

class SaveMessageToChatUseCaseImpl@Inject constructor(private val repository: Repository): SaveMessageToChatUseCase{
    override suspend fun invoke(message: Message) {
        repository.saveMessage(message)
    }
}