package ru.mvrlrd.core_impl.database.chatdatabase.di

import android.content.ContentValues
import android.content.Context
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.mvrlrd.core_api.database.chat.ChatDao
import ru.mvrlrd.core_api.database.chat.ChatDatabaseContract
import ru.mvrlrd.core_impl.database.chatdatabase.ChatDatabase
import javax.inject.Singleton

@Module
class ChatDatabaseModule {

    @Provides
    @Reusable
    fun provideChatDao(contract: ChatDatabaseContract): ChatDao {
        return contract.chatDao()
    }

    @Provides
    @Singleton
    fun provideChatDatabase(context: Context): ChatDatabaseContract {
        val rdc = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.insert("chat_rooms", OnConflictStrategy.REPLACE,
                    ContentValues(
                    ).apply {
                        put("chatId", 1L)
                        put("title", "QA")
                        put("roleText", "умный ассистент")
                        put("prompt", true)
                        put("modelVer", "1.0")
                        put("date", System.currentTimeMillis())
                        put("stream", false)
                        put("temperature", 0.5)
                        put("maxTokens", "1000")
                        put("inputTokens", 0)
                        put("completionTokens", 0)
                        put("totalTokens", 0)
                    })
                db.insert("chat_rooms", OnConflictStrategy.REPLACE,
                    ContentValues(
                    ).apply {
                        put("chatId", 2L)
                        put("title", "SimpleChat")
                        put("roleText", "умный ассистент")
                        put("prompt", false)
                        put("modelVer", "1.0")
                        put("date", System.currentTimeMillis())
                        put("stream", false)
                        put("temperature", 0.4)
                        put("maxTokens", "1000")
                        put("inputTokens", 0)
                        put("completionTokens", 0)
                        put("totalTokens", 0)
                    })
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

        val db =
            Room.inMemoryDatabaseBuilder(
//        Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
//            "chat_gpt_database"
        )
                .addCallback(rdc)
            .fallbackToDestructiveMigration()
            .build()

        return db
    }
}
