package ru.mvrlrd.favorites.data

import ru.mvrlrd.core_api.database.chat.entity.Chat
import ru.mvrlrd.favorites.domain.ChatEntity
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun mapChatEntityToChat(entity: ChatEntity): Chat{
        return Chat(
            entity.chatId,
            entity.title,
            entity.date
        )
    }
    fun mapChatToChatEntity(chat: Chat): ChatEntity{
        return ChatEntity(
            chat.id,
            chat.title,
            chat.date
        )
    }
    fun mapChatsToEntities(chats: List<Chat>):List<ChatEntity>{
        return chats.map { chat-> mapChatToChatEntity(chat) }
    }
}