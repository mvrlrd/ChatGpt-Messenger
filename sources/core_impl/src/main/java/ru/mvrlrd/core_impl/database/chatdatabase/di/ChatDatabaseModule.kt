package ru.mvrlrd.core_impl.database.chatdatabase.di

import android.content.Context
import androidx.room.Room
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
        return Room.inMemoryDatabaseBuilder(
            context,
            ChatDatabase::class.java,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
