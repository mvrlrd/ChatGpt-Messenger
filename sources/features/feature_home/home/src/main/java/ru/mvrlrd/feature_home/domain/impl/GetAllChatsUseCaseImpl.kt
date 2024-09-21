package ru.mvrlrd.feature_home.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mvrlrd.feature_home.data.ColorRepository
import ru.mvrlrd.feature_home.domain.ChatForHome
import ru.mvrlrd.feature_home.domain.ChatRepository
import ru.mvrlrd.feature_home.domain.api.GetAllChatsUseCase
import javax.inject.Inject

class GetAllChatsUseCaseImpl @Inject constructor(
    private val repo: ChatRepository,
    private val colorRepository: ColorRepository
) : GetAllChatsUseCase {
    override suspend fun invoke(): Flow<List<ChatForHome>> {
        val chats = repo.getAllChats().map { chats ->
            chats.map {chatForHome->
                chatForHome.copy(
                    color = colorRepository.colors[(chatForHome.chatId % colorRepository.colors.size).toInt()]
                )
            }
        }
        return chats
    }
}