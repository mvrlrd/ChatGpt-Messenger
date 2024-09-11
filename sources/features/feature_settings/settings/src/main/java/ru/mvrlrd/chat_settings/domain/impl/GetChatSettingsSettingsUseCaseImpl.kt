package ru.mvrlrd.chat_settings.domain.impl

import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.SettingsRepository
import ru.mvrlrd.chat_settings.domain.api.GetChatSettingsUseCase
import javax.inject.Inject

class GetChatSettingsSettingsUseCaseImpl@Inject constructor(private val repository: SettingsRepository, ): GetChatSettingsUseCase {
    override suspend fun invoke(chatId: Long): ChatSettings {
        return repository.getChat(chatId)
    }
}