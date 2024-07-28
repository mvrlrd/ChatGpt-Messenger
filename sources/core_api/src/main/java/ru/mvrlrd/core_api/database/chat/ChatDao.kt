package ru.mvrlrd.core_api.database.chat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Chat
import ru.mvrlrd.core_api.database.chat.entity.ChatWithMessages
import ru.mvrlrd.core_api.database.chat.entity.Message

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_rooms")
    fun getAllChats(): Flow<List<Chat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat): Long

    @Query("DELETE FROM chat_rooms WHERE chatId = :id")
    suspend fun removeChat(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message): Long

    @Transaction
    @Query("SELECT * FROM chat_rooms WHERE chatId = :chatId")
    fun getChatWithMessages(chatId: Long): Flow<ChatWithMessages>

    @Query("SELECT * FROM messages WHERE holderChatId = :chatId")
    fun getMessagesForChat(chatId: Long): Flow<List<Message>>

    @Query("DELETE FROM messages WHERE messageId = :messageId")
    suspend fun deleteMessage(messageId: Long)

    @Query("DELETE FROM messages WHERE holderChatId = :chatId")
    suspend fun clearMessages(chatId: Long)
}