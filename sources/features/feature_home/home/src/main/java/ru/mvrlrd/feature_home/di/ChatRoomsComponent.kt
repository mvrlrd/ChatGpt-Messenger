package ru.mvrlrd.feature_home.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.feature_home.domain.api.CreateChatUseCase
import ru.mvrlrd.feature_home.domain.api.GetAllChatsUseCase
import ru.mvrlrd.feature_home.domain.api.RemoveChatUseCase

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [ChatRoomsModule::class]
)
interface ChatRoomsComponent {

    fun createChatUseCase(): CreateChatUseCase
    fun getAllChatsUseCase(): GetAllChatsUseCase
    fun removeChatUseCase(): RemoveChatUseCase
    companion object {
        fun create(providersFacade: ProvidersFacade): ChatRoomsComponent =
            DaggerChatRoomsComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}