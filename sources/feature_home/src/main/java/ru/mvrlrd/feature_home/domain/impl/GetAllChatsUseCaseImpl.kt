package ru.mvrlrd.feature_home.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.GetAllChatsUseCase

import javax.inject.Inject

class GetAllChatsUseCaseImpl@Inject constructor(private val repo: ChatRepository): GetAllChatsUseCase {
    override suspend fun invoke(): Flow<List<Chat>> {
        return repo.getAllChats()
    }
}