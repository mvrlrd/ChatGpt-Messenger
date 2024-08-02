package ru.mvrlrd.core_factory


import ru.mvrlrd.core_api.database.answer.DatabaseProvider
import ru.mvrlrd.core_api.database.chat.ChatDatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_api.network.NetworkClientProvider
import ru.mvrlrd.core_impl.database.chatdatabase.di.DaggerChatDatabaseComponent
import ru.mvrlrd.core_impl.database.di.DaggerDatabaseComponent
import ru.mvrlrd.core_impl.network.di.DaggerNetworkComponent

object CoreProvidersFactory {
    fun createNetworkClient(appProvider: AppProvider): NetworkClientProvider {
        return DaggerNetworkComponent.builder().appProvider(appProvider).build()
    }

    fun createDatabaseComponent(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }

    fun createChatDatabaseComponent(appProvider: AppProvider): ChatDatabaseProvider {
        return DaggerChatDatabaseComponent.builder().appProvider(appProvider).build()
    }

}