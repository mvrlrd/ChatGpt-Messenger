package ru.mvrlrd.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.favorites.domain.ChatEntity
import ru.mvrlrd.favorites.domain.ChatRepository
import ru.mvrlrd.favorites.domain.api.GetAllChatsUseCase

import javax.inject.Inject

class GetAllChatsUseCaseImpl@Inject constructor(val repo: ChatRepository): GetAllChatsUseCase {
    override suspend fun invoke(): Flow<List<ChatEntity>> {
        return repo.getAllChats()
    }
}