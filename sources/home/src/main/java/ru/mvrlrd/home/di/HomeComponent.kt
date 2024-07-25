package ru.mvrlrd.home.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.RemoteRepository
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


    companion object {
        fun create(providersFacade: ProvidersFacade): HomeComponent =
            DaggerHomeComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}