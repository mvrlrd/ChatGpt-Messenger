package ru.mvrlrd.home.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.home.domain.api.ClearMessagesUseCase
import ru.mvrlrd.home.domain.api.DeleteMessageUseCase
import ru.mvrlrd.home.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.home.domain.api.SaveMessageToChatUseCase

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun getRepo(): RemoteRepository

    fun provideSaveMessageToChatUseCase(): SaveMessageToChatUseCase
    fun provideGetAllMessagesForChatUseCase(): GetAllMessagesForChatUseCase
    fun provideDeleteMessageUseCase(): DeleteMessageUseCase
    fun provideClearMessagesUseCase(): ClearMessagesUseCase


    companion object {
        fun create(providersFacade: ProvidersFacade): HomeComponent =
            DaggerHomeComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}