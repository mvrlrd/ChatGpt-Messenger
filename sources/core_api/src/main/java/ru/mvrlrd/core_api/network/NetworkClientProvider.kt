package ru.mvrlrd.core_api.network

import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository

interface NetworkClientProvider {
    fun provideNetworkClient(): NetworkClient
    fun provideRemoteRepo(): RemoteRepository
}