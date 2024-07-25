package ru.mvrlrd.home.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.GetAllMessagesForChatUseCase
import javax.inject.Inject

class GetAllMessagesForChatUseCaseImpl@Inject constructor(private val repository: Repository): GetAllMessagesForChatUseCase {
    override fun invoke(chatId: Long): Flow<List<Message>> {
        return repository.getAllMessages(chatId)
    }
}