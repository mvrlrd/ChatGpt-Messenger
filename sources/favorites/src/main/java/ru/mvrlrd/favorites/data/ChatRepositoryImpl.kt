package ru.mvrlrd.favorites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.favorites.domain.ChatEntity
import ru.mvrlrd.favorites.domain.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl@Inject constructor(private val dao: ChatDao, private val mapper: ChatMapper): ChatRepository {
    override suspend fun getAllChats(): Flow<List<ChatEntity>> {
        return flow {
            dao.getAllChats().map { chat -> mapper.mapChatToChatEntity(chat) }
        }
    }

    override suspend fun createChat(entity: ChatEntity) {
        dao.insertChat(mapper.mapChatEntityToChat(entity))
    }
}