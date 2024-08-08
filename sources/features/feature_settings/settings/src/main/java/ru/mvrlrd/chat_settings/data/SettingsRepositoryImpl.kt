package ru.mvrlrd.chat_settings.data

import ru.mvrlrd.base_chat_home.model.ChatMapper
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.SettingsRepository
import ru.mvrlrd.core_api.database.chat.ChatDao
import javax.inject.Inject

class SettingsRepositoryImpl@Inject constructor(private val chatDao: ChatDao, private val mapper: SettingsMapper) : SettingsRepository{
    override suspend fun saveSettings(chatSettings: ChatSettings) {
        val chatEntity = mapper.mapSettingsToChatEntity(chatSettings)
        chatDao.upsertChat(chatEntity)
    }

    override suspend fun getChat(chatId: Long): ChatSettings {
        return mapper.mapChatEntityToSettings(chatDao.getChat(chatId))
    }
}