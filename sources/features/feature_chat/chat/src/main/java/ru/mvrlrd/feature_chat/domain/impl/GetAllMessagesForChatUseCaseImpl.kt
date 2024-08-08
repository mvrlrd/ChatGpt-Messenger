package ru.mvrlrd.feature_chat.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import javax.inject.Inject

class GetAllMessagesForChatUseCaseImpl@Inject constructor(private val chatRepository: ChatRepository): GetAllMessagesForChatUseCase {
    override fun invoke(chatId: Long): Flow<List<MessageEntity>> {
        return chatRepository.getAllMessages(chatId)
    }
}