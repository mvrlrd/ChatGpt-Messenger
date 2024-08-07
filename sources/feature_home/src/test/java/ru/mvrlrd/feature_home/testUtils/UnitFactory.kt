package ru.mvrlrd.feature_home.testUtils

import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.base_chat_home.Chat

object UnitFactory {
    fun getChat(): ChatEntity = ChatEntity(
            1L,
            "chat#1",
            1627890123456L
        )
    fun getChatEntity(): ru.mvrlrd.base_chat_home.Chat =
        ru.mvrlrd.base_chat_home.Chat(
            1L,
            "chat#1",
            1627890123456L
        )

    fun getChatList(): List<ChatEntity> = listOf(getChat())

    fun getChatEntityList(): List<ru.mvrlrd.base_chat_home.Chat> = listOf(getChatEntity())

}