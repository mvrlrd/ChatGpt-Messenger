package ru.mvrlrd.favorites.testUtils

import ru.mvrlrd.core_api.database.chat.entity.Chat
import ru.mvrlrd.favorites.domain.ChatEntity

object UnitFactory {
    fun getChat(): Chat = Chat(
            1L,
            "chat#1",
            1627890123456L
        )
    fun getChatEntity(): ChatEntity =
        ChatEntity(
            1L,
            "chat#1",
            1627890123456L
        )

    fun getChatList(): List<Chat> = listOf(getChat())

    fun getChatEntityList(): List<ChatEntity> = listOf(getChatEntity())

}