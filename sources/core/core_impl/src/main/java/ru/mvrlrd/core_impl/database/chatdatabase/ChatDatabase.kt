package ru.mvrlrd.core_impl.database.chatdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mvrlrd.core_api.database.chat.ChatDatabaseContract
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity

@Database(entities = [ChatEntity::class, MessageEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase(), ChatDatabaseContract