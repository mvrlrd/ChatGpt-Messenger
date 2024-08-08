package ru.mvrlrd.feature_home.domain.api

import ru.mvrlrd.base_chat_home.model.Chat


interface CreateChatUseCase {
    suspend operator fun invoke(chat: Chat)
}