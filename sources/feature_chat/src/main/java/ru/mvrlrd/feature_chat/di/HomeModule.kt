package ru.mvrlrd.feature_chat.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.feature_chat.data.RepositoryImpl
import ru.mvrlrd.feature_chat.domain.Repository
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import ru.mvrlrd.feature_chat.domain.impl.ClearMessagesUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.DeleteMessageUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.GetAllMessagesForChatUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.GetAnswerUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.SaveMessageToChatUseCaseImpl

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