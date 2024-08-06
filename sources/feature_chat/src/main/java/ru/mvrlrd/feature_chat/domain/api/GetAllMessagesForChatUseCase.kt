package ru.mvrlrd.feature_chat.domain.api

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Message

interface GetAllMessagesForChatUseCase {
    operator fun invoke(chatId: Long): Flow<List<Message>>
}