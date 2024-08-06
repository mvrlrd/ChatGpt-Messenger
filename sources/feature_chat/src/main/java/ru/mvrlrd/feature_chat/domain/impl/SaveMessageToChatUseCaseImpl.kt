package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import javax.inject.Inject

class SaveMessageToChatUseCaseImpl@Inject constructor(private val repository: Repository): SaveMessageToChatUseCase{
    override suspend fun invoke(message: Message) {
        repository.saveMessage(message)
    }
}