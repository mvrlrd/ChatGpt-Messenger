package ru.mvrlrd.core_api.database.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mvrlrd.core_api.database.chat.entity.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_rooms")
    fun getAllChats(): Flow<List<Chat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat): Long
}