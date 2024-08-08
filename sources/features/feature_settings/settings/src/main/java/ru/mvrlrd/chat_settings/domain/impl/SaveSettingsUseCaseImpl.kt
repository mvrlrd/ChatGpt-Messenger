package ru.mvrlrd.chat_settings.domain.impl

import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.SettingsRepository
import ru.mvrlrd.chat_settings.domain.api.SaveSettingsUseCase
import javax.inject.Inject

class SaveSettingsUseCaseImpl@Inject constructor(private val repository: SettingsRepository): SaveSettingsUseCase {
    override suspend fun invoke(chatSettings: ChatSettings) {
        repository.saveSettings(chatSettings)
    }
}