package ru.mvrlrd.chat_settings.domain.api

import ru.mvrlrd.chat_settings.domain.ChatSettings

interface GetChatSettingsUseCase{
    suspend operator fun invoke(chatId: Long): ChatSettings
}