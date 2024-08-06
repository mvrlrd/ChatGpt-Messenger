package ru.mvrlrd.feature_home.domain.impl

import ru.mvrlrd.feature_home.domain.ChatEntity
import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.CreateChatUseCase
import javax.inject.Inject

class CreateChatUseCaseImpl@Inject constructor(private val repo: ChatRepository): CreateChatUseCase {
    override suspend fun invoke(entity: ChatEntity) {
        repo.createChat(entity)
    }
}