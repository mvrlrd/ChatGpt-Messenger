package ru.mvrlrd.feature_home.domain.api

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.base_chat_home.model.Chat

interface GetAllChatsUseCase {
    suspend operator fun invoke(): Flow<List<Chat>>
}