package ru.mvrlrd.feature_home.domain.impl

import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.CreateChatUseCase
import javax.inject.Inject

class CreateChatUseCaseImpl@Inject constructor(private val repo: ChatRepository): CreateChatUseCase {
    override suspend fun invoke(entity: Chat) {
        repo.createChat(entity)
    }
}