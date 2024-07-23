package ru.mvrlrd.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.favorites.domain.ChatEntity

interface GetAllChatsUseCase {
    suspend operator fun invoke(): Flow<List<ChatEntity>>
}