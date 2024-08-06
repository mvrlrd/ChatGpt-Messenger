package ru.mvrlrd.feature_chat.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun provideSaveMessageToChatUseCase(): SaveMessageToChatUseCase
    fun provideGetAllMessagesForChatUseCase(): GetAllMessagesForChatUseCase
    fun provideDeleteMessageUseCase(): DeleteMessageUseCase
    fun provideClearMessagesUseCase(): ClearMessagesUseCase
    fun provideGetAnswerUseCase(): GetAnswerUseCase


    companion object {
        fun create(providersFacade: ProvidersFacade): HomeComponent =
            DaggerHomeComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}