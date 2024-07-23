package ru.mvrlrd.core_impl.database.chatdatabase.di

import dagger.Component
import ru.mvrlrd.core_api.database.chat.ChatDatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_impl.database.di.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [ChatDatabaseModule::class]
)
interface ChatDatabaseComponent: ChatDatabaseProvider