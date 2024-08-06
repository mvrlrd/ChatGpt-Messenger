package ru.mvrlrd.feature_chat.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import javax.inject.Inject

class GetAllMessagesForChatUseCaseImpl@Inject constructor(private val repository: Repository): GetAllMessagesForChatUseCase {
    override fun invoke(chatId: Long): Flow<List<Message>> {
        return repository.getAllMessages(chatId)
    }
}