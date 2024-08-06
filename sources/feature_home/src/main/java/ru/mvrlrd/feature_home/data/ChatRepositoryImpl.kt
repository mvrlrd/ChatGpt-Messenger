package ru.mvrlrd.feature_home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.feature_home.domain.ChatEntity
import ru.mvrlrd.feature_home.domain.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatDao,
    private val mapper: ChatMapper
) : ChatRepository {
    override suspend fun getAllChats(): Flow<List<ChatEntity>> {
        return dao.getAllChats().map { chats ->
            mapper.mapDtosToEntities(chats)
        }
    }

    override suspend fun createChat(entity: ChatEntity) {
        dao.insertChat(mapper.mapEntityToDto(entity))
    }

    override suspend fun removeChat(id: Long) {
        dao.removeChat(id)
    }
}