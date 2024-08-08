package ru.mvrlrd.core_api.network

interface NetworkClientProvider {
    fun provideNetworkClient(): NetworkClient
    fun provideRemoteRepository(): RemoteRepository
}