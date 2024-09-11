package ru.mvrlrd.chat_settings.domain.api

import ru.mvrlrd.chat_settings.domain.ChatSettings

interface SaveSettingsUseCase {
    suspend operator fun invoke(chatSettings: ChatSettings)
}