package ru.mvrlrd.home

import dagger.Component
import dagger.Provides
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.RemoteRepository

@Component(dependencies = [ru.mvrlrd.core_api.mediators.ProvidersFacade::class])
interface HomeComponent{

    fun getRepo(): RemoteRepository

        companion object{
            fun create(providersFacade: ru.mvrlrd.core_api.mediators.ProvidersFacade): HomeComponent =
                DaggerHomeComponent.builder()
                    .providersFacade(providersFacade)
                    .build()
        }
    }