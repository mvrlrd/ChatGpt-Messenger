package ru.mvrlrd.feature_chat.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.feature_chat.data.ChatRepositoryImpl
import ru.mvrlrd.feature_chat.domain.ChatRepository
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import ru.mvrlrd.feature_chat.domain.impl.ClearMessagesUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.DeleteMessageUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.GetAllMessagesForChatUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.GetAnswerUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.GetChatSettingsUseCaseImpl
import ru.mvrlrd.feature_chat.domain.impl.SaveMessageToChatUseCaseImpl

@Module
interface HomeModule {

    @Binds
    fun bindRepository(repository: ChatRepositoryImpl): ChatRepository
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

    @Binds
    fun bindGetChatUseCase(getChatSettingsUseCaseImpl: GetChatSettingsUseCaseImpl): GetChatSettingsUseCase
}