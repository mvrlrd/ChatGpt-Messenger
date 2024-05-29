package ru.mvrlrd.companion

import dagger.Component
import ru.mvrlrd.core.CoreProvidersFactory
import ru.mvrlrd.core_api.mediators.NetworkClientProvider
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.RemoteRepository
import javax.inject.Singleton

@Singleton
@Component(dependencies = [NetworkClientProvider::class])
interface FacadeComponent : ProvidersFacade {

    fun getRepo(): RemoteRepository

    companion object {
        fun init(): FacadeComponent =
            DaggerFacadeComponent.builder()
                .networkClientProvider(CoreProvidersFactory.createNetworkClient())
                .build()
    }
}