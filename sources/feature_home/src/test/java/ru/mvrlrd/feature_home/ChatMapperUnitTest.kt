package ru.mvrlrd.feature_home

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.mvrlrd.core_api.database.chat.entity.Chat
import ru.mvrlrd.feature_home.data.ChatMapper
import ru.mvrlrd.feature_home.testUtils.UnitFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ChatMapperUnitTest {
    private lateinit var mapper: ChatMapper

    @Before
    fun setup() {
        mapper = ChatMapper()
    }

    @Test
    fun `test mapping entity to chat`() {
        val entity = UnitFactory.getChatEntity()
        val expectedChat = UnitFactory.getChat()
        val actual = mapper.mapChatEntityToChat(entity)
        assertEquals("Mapping ChatEntity to Chat failed", expectedChat, actual)
    }

    @Test
    fun `test mapping chat to entity`() {
        val chat = UnitFactory.getChat()
        val expectedEntity = UnitFactory.getChatEntity()
        val actual = mapper.mapChatToChatEntity(chat)
        assertEquals("Mapping Chat to Entity failed", expectedEntity, actual)
    }

    @Test
    fun `test mapping chats to entities`() {
        val expectedEntityList = UnitFactory.getChatEntityList()
        val actualList = mapper.mapChatsToEntities(UnitFactory.getChatList())
        assertEquals("Mapping Chats to Entities failed", expectedEntityList, actualList)
    }

    @Test
    fun `test mapping empty list of chats to entities`() {
        val chats = emptyList<Chat>()
        val actualList = mapper.mapChatsToEntities(chats)
        assertTrue(actualList.isEmpty())
    }
}