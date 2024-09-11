package ru.mvrlrd.feature_home

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.mvrlrd.base_chat_home.model.ChatMapper
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.feature_home.testUtils.UnitFactory

class ChatEntityMapperUnitTest {
    private lateinit var mapper: ChatMapper

    @Before
    fun setup() {
        mapper = ChatMapper()
    }

    @Test
    fun `test mapping chatEntity to chat`() {
        val expectedChat = UnitFactory.getChat()
        val actual =
            mapper.mapChatEntityToChat(UnitFactory.getChatEnt().copy(date = expectedChat.date))
        assertEquals("Mapping ChatEntity to Chat failed", expectedChat, actual)
    }

    @Test
    fun `test mapping chat to chatEntity`() {
        val expectedChatEntity = UnitFactory.getChatEnt()
        val actualEntity =
            mapper.mapChatToChatEntity(UnitFactory.getChat().copy(date = expectedChatEntity.date))
        assertEquals("Mapping Chat to ChatEntity failed", expectedChatEntity, actualEntity)
    }

    @Test
    fun `test mapping chat to entity`() {
        val expectedEntity = UnitFactory.getChatEnt()
        val actualEntity =
            mapper.mapChatToChatEntity(UnitFactory.getChat().copy(date = expectedEntity.date))
        assertEquals("Mapping Chat to Entity failed", expectedEntity, actualEntity)
    }

    @Test
    fun `test mapping entity to chat`() {
        val expectedEntity = UnitFactory.getChat()
        val actualEntity =
            mapper.mapChatEntityToChat(UnitFactory.getChatEnt().copy(date = expectedEntity.date))
        assertEquals("Mapping entity to chat failed", expectedEntity, actualEntity)
    }

    @Test
    fun `test mapping CompletionOptions To CompletionOptionsEntity`() {
        val expectedCompletionOptionsEntity = UnitFactory.getCompletionOptionsEntity()
        val actualCompletionOptionsEntity =
            mapper.mapCompletionOptionsToCompletionOptionsEntity(UnitFactory.getCompletionOptions())
        assertEquals(expectedCompletionOptionsEntity, actualCompletionOptionsEntity)
    }

    @Test
    fun `test mapping CompletionOptionsEntity To CompletionOptions`() {
        val expectedCompletionOptions = UnitFactory.getCompletionOptions()
        val actualCompletionOptions =
            mapper.mapCompletionOptionsEntityToCompletionOptions(UnitFactory.getCompletionOptionsEntity())
        assertEquals(expectedCompletionOptions, actualCompletionOptions)
    }

    @Test
    fun `test mapping usageEntity to usage`() {
        val expectedUsage = UnitFactory.getUsage()
        val actualUsage = mapper.mapUsageEntityToUsage(UnitFactory.getUsageEntity())
        assertEquals(expectedUsage, actualUsage)
    }

    @Test
    fun `test mapping usage to usageEntity`() {
        val expectedUsageEntity = UnitFactory.getUsageEntity()
        val actualUsageEntity = mapper.mapUsageToUsageEntity(UnitFactory.getUsage())
        assertEquals(expectedUsageEntity, actualUsageEntity)
    }


    @Test
    fun `test mapping chatEntities to chats`() {
        val chat = UnitFactory.getChat()
        val expectedList = listOf(chat)
        val chatEntity = UnitFactory.getChatEnt().copy(date = chat.date)
        val chatEntities = listOf(chatEntity)
        val actualList = mapper.mapChatEntitiesToChats(chatEntities)
        assertEquals("Mapping Chats to Entities failed", expectedList, actualList)
    }

    @Test
    fun `test mapping empty list of chatEntities to chats`() {
        val chatEntities = emptyList<ChatEntity>()
        val actualList = mapper.mapChatEntitiesToChats(chatEntities)
        assertTrue(actualList.isEmpty())
    }
}