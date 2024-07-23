package ru.mvrlrd.favorites.domain.impl

import ru.mvrlrd.favorites.domain.ChatEntity
import ru.mvrlrd.favorites.domain.ChatRepository
import ru.mvrlrd.favorites.domain.api.CreateChatUseCase
import javax.inject.Inject

class CreateChatUseCaseImpl@Inject constructor(private val repo: ChatRepository): CreateChatUseCase {
    override suspend fun invoke(entity: ChatEntity) {
        repo.createChat(entity)
    }
}