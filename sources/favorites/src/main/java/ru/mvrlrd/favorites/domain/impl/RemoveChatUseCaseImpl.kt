package ru.mvrlrd.favorites.domain.impl

import ru.mvrlrd.favorites.domain.ChatRepository
import ru.mvrlrd.favorites.domain.api.RemoveChatUseCase
import javax.inject.Inject

class RemoveChatUseCaseImpl@Inject constructor(private val repository: ChatRepository) : RemoveChatUseCase{
    override suspend  operator fun invoke(id: Long) {
        repository.removeChat(id)
    }
}