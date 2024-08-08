package ru.mvrlrd.feature_home.domain.impl

import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.RemoveChatUseCase
import javax.inject.Inject

class RemoveChatUseCaseImpl@Inject constructor(private val repository: ChatRepository) : RemoveChatUseCase{
    override suspend  operator fun invoke(id: Long) {
        repository.removeChat(id)
    }
}