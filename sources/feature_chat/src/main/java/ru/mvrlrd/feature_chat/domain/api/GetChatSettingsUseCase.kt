package ru.mvrlrd.feature_chat.domain.api

import ru.mvrlrd.base_chat_home.model.Chat

interface GetChatSettingsUseCase {
    suspend operator fun invoke(chatId:Long): Result<Chat>
}