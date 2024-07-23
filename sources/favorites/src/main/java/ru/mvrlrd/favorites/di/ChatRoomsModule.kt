package ru.mvrlrd.favorites.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.favorites.data.ChatRepositoryImpl
import ru.mvrlrd.favorites.domain.ChatRepository
import ru.mvrlrd.favorites.domain.api.CreateChatUseCase
import ru.mvrlrd.favorites.domain.api.GetAllChatsUseCase
import ru.mvrlrd.favorites.domain.impl.CreateChatUseCaseImpl
import ru.mvrlrd.favorites.domain.impl.GetAllChatsUseCaseImpl

@Module
interface ChatRoomsModule {

    @Binds
    fun bindsChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindsGetAllChatsUseCase(getAllChatsUseCaseImpl: GetAllChatsUseCaseImpl): GetAllChatsUseCase

    @Binds
    fun bindsCreateChatUseCase(createChatUseCaseImpl: CreateChatUseCaseImpl): CreateChatUseCase
}