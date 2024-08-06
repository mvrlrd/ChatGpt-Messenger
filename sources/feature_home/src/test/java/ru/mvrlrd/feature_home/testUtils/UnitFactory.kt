package ru.mvrlrd.feature_home.testUtils

import ru.mvrlrd.core_api.database.chat.entity.ChatDto
import ru.mvrlrd.feature_home.domain.ChatEntity

object UnitFactory {
    fun getChat(): ChatDto = ChatDto(
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

    fun getChatList(): List<ChatDto> = listOf(getChat())

    fun getChatEntityList(): List<ChatEntity> = listOf(getChatEntity())

}