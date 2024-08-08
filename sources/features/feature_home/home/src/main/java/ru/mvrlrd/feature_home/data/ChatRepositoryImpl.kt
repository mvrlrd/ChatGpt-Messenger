package ru.mvrlrd.feature_home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.ChatMapper
import ru.mvrlrd.feature_home.domain.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatDao,
    private val mapper: ChatMapper
) : ChatRepository {
    override suspend fun getAllChats(): Flow<List<Chat>> {
        return dao.getAllChats().map { chats ->
            mapper.mapChatEntitiesToChats(chats)
        }
    }

    override suspend fun removeChat(id: Long) {
        dao.removeChat(id)
    }
}