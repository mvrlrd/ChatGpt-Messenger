package ru.mvrlrd.core_impl.network.di

import android.content.Context
import dagger.Component
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_api.network.NetworkClientProvider

@Component(
    dependencies = [AppProvider::class],
    modules = [NetworkModule::class])
interface NetworkComponent: NetworkClientProvider {

    fun provideContext(): Context


}