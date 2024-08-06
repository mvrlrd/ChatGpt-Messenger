package ru.mvrlrd.feature_home.domain.api

import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.feature_home.domain.ChatEntity

interface GetAllChatsUseCase {
    suspend operator fun invoke(): Flow<List<ChatEntity>>
}