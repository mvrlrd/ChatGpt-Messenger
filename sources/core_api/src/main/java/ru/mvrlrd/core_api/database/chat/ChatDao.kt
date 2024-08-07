package ru.mvrlrd.core_api.database.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.ChatWithMessages
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_rooms")
    fun getAllChats(): Flow<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chatEntity: ChatEntity): Long

    @Query("SELECT * FROM chat_rooms WHERE chatId = :id")
    suspend fun getChat(id: Long): ChatEntity

    @Query("DELETE FROM chat_rooms WHERE chatId = :id")
    suspend fun removeChat(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageEntity: MessageEntity): Long

    @Transaction
    @Query("SELECT * FROM chat_rooms WHERE chatId = :chatId")
    fun getChatWithMessages(chatId: Long): Flow<ChatWithMessages>

    @Query("SELECT * FROM messages WHERE holderChatId = :chatId")
    fun getMessagesForChat(chatId: Long): Flow<List<MessageEntity>>

    @Query("DELETE FROM messages WHERE messageId = :messageId")
    suspend fun deleteMessage(messageId: Long)

    @Query("DELETE FROM messages WHERE holderChatId = :chatId")
    suspend fun clearMessages(chatId: Long)
}