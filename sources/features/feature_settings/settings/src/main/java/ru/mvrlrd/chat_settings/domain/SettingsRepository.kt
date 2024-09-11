package ru.mvrlrd.chat_settings.domain

import ru.mvrlrd.base_chat_home.model.Chat

interface SettingsRepository {
    suspend fun saveSettings(chatSettings: ChatSettings)
    suspend fun getChat(chatId: Long): ChatSettings
}