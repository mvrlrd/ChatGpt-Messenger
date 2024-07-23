package ru.mvrlrd.favorites.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.favorites.domain.api.CreateChatUseCase

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [ChatRoomsModule::class]
)
interface ChatRoomsComponent {

    fun createChatUseCase(): CreateChatUseCase
    companion object {
        fun create(providersFacade: ProvidersFacade): ChatRoomsComponent =
            DaggerChatRoomsComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}