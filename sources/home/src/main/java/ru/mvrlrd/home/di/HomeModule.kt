package ru.mvrlrd.home.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.home.data.RepositoryImpl
import ru.mvrlrd.home.domain.Repository
import ru.mvrlrd.home.domain.api.ClearMessagesUseCase
import ru.mvrlrd.home.domain.api.DeleteMessageUseCase
import ru.mvrlrd.home.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.home.domain.api.GetAnswerUseCase
import ru.mvrlrd.home.domain.api.SaveMessageToChatUseCase
import ru.mvrlrd.home.domain.impl.ClearMessagesUseCaseImpl
import ru.mvrlrd.home.domain.impl.DeleteMessageUseCaseImpl
import ru.mvrlrd.home.domain.impl.GetAllMessagesForChatUseCaseImpl
import ru.mvrlrd.home.domain.impl.GetAnswerUseCaseImpl
import ru.mvrlrd.home.domain.impl.SaveMessageToChatUseCaseImpl

@Module
interface HomeModule {

    @Binds
    fun bindRepository(repository: RepositoryImpl): Repository
    @Binds
    fun bindSaveMessageToChatUseCase(a: SaveMessageToChatUseCaseImpl): SaveMessageToChatUseCase

    @Binds
    fun bindGetAllMessagesForChatUseCase(a: GetAllMessagesForChatUseCaseImpl): GetAllMessagesForChatUseCase

    @Binds
    fun bindDeleteMessageUseCase(deleteMessageUseCaseImpl: DeleteMessageUseCaseImpl): DeleteMessageUseCase

    @Binds
    fun bindClearMessagesUseCase(clearMessagesUseCaseImpl: ClearMessagesUseCaseImpl): ClearMessagesUseCase

    @Binds
    fun bindGetAnswerUseCase(getAnswerUseCaseImpl: GetAnswerUseCaseImpl): GetAnswerUseCase
}