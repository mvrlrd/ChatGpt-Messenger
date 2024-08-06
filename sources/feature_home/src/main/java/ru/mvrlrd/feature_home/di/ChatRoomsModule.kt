package ru.mvrlrd.feature_home.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.feature_home.data.ChatRepositoryImpl
import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.CreateChatUseCase
import ru.mvrlrd.feature_home.domain.api.GetAllChatsUseCase
import ru.mvrlrd.feature_home.domain.api.RemoveChatUseCase
import ru.mvrlrd.feature_home.domain.impl.CreateChatUseCaseImpl
import ru.mvrlrd.feature_home.domain.impl.GetAllChatsUseCaseImpl
import ru.mvrlrd.feature_home.domain.impl.RemoveChatUseCaseImpl

@Module
interface ChatRoomsModule {

    @Binds
    fun bindsChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindsGetAllChatsUseCase(getAllChatsUseCaseImpl: GetAllChatsUseCaseImpl): GetAllChatsUseCase

    @Binds
    fun bindsCreateChatUseCase(createChatUseCaseImpl: CreateChatUseCaseImpl): CreateChatUseCase

    @Binds
    fun bindsRemoveChatUseCase(removeChatUseCaseImpl: RemoveChatUseCaseImpl): RemoveChatUseCase
}