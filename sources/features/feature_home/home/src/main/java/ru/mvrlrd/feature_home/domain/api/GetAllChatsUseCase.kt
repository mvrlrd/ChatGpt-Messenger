package ru.mvrlrd.feature_home.domain.api

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.feature_home.domain.ChatForHome

interface GetAllChatsUseCase {
    suspend operator fun invoke(): Flow<List<ChatForHome>>
}