package ru.mvrlrd.core

import ru.mvrlrd.core_api.mediators.NetworkClientProvider
import ru.mvrlrd.ktor_module.di.DaggerKtorComponent

object CoreProvidersFactory {
    fun createNetworkClient(): NetworkClientProvider {
        return DaggerKtorComponent.create()
    }
}