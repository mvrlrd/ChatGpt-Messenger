package ru.mvrlrd.core_impl.network2.di

import dagger.Component
import ru.mvrlrd.core_api.network.NetworkClientProvider

@Component(
    modules = [NetworkModule::class])
interface NetworkComponent: NetworkClientProvider {
}