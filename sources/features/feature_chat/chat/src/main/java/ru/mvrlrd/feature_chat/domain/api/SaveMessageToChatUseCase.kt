package ru.mvrlrd.feature_chat.domain.api

import ru.mvrlrd.core_api.database.chat.entity.MessageEntity

interface SaveMessageToChatUseCase {
    suspend operator fun invoke(messageEntity: MessageEntity)
}