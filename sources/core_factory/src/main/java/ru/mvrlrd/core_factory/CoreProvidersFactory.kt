package ru.mvrlrd.core_factory


import ru.mvrlrd.core_api.database.DatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_api.network.NetworkClientProvider
import ru.mvrlrd.core_impl.database.DaggerDatabaseComponent
import ru.mvrlrd.core_impl.network.di.DaggerKtorComponent

object CoreProvidersFactory {
    fun createNetworkClient(): NetworkClientProvider {
        return DaggerKtorComponent.create()
    }

    fun createDatabaseComponent(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }

}