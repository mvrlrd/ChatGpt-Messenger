package ru.mvrlrd.feature_chat.domain.impl

import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.GetChatSettingsUseCase
import javax.inject.Inject

class GetChatSettingsUseCaseImpl @Inject constructor(private val repository: ChatRepository): GetChatSettingsUseCase {
    override suspend fun invoke(chatId: Long): Result<Chat> {
        return repository.getChatSettings(chatId)
    }
}