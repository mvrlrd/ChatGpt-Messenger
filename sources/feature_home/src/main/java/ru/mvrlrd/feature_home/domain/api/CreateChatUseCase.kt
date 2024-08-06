package ru.mvrlrd.feature_home.domain.api

import ru.mvrlrd.feature_home.domain.ChatEntity

interface CreateChatUseCase {
    suspend operator fun invoke(chat: ChatEntity)
}