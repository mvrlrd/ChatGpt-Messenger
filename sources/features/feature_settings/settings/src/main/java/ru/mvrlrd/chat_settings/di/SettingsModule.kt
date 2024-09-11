package ru.mvrlrd.chat_settings.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.chat_settings.data.SettingsRepositoryImpl
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.SettingsRepository
import ru.mvrlrd.chat_settings.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.chat_settings.domain.api.SaveSettingsUseCase
import ru.mvrlrd.chat_settings.domain.impl.GetChatSettingsSettingsUseCaseImpl
import ru.mvrlrd.chat_settings.domain.impl.SaveSettingsUseCaseImpl

@Module
interface SettingsModule {
    @Binds
    fun bindSaveChatSettingsUseCase(saveSettingsUseCaseImpl: SaveSettingsUseCaseImpl): SaveSettingsUseCase

    @Binds
    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindGetChatSettingsUseCase(getChatSettingsSettingsUseCaseImpl: GetChatSettingsSettingsUseCaseImpl): GetChatSettingsUseCase
}