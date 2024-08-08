package ru.mvrlrd.chat_settings.di

import dagger.Component
import ru.mvrlrd.chat_settings.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.chat_settings.domain.api.SaveSettingsUseCase
import ru.mvrlrd.core_api.mediators.ProvidersFacade

@Component(dependencies = [ProvidersFacade::class],
    modules = [SettingsModule::class])
interface SettingsComponent {

    fun provideSaveSettingsUseCase(): SaveSettingsUseCase
    fun provideGetChatSettingsUseCase(): GetChatSettingsUseCase
    companion object{
        fun create(providersFacade: ProvidersFacade): SettingsComponent{
            return DaggerSettingsComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
    }
}