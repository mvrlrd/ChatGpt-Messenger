package ru.mvrlrd.favorites.domain.api

import ru.mvrlrd.favorites.domain.ChatEntity

interface CreateChatUseCase {
    suspend operator fun invoke(chat: ChatEntity)
}