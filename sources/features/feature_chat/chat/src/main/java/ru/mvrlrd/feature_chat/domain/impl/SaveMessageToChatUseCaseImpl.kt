package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import javax.inject.Inject

class SaveMessageToChatUseCaseImpl@Inject constructor(private val chatRepository: ChatRepository): SaveMessageToChatUseCase{
    override suspend fun invoke(messageEntity: MessageEntity) {
        chatRepository.saveMessage(messageEntity)
    }
}