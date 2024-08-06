package ru.mvrlrd.feature_chat.domain.api

import ru.mvrlrd.core_api.database.chat.entity.Message

interface SaveMessageToChatUseCase {
    suspend operator fun invoke(message: Message)
}