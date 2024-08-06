package ru.mvrlrd.companion

import android.app.Application
import dagger.Component
import ru.mvrlrd.core_api.database.chat.ChatDatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.NetworkClientProvider
import ru.mvrlrd.core_factory.CoreProvidersFactory

@Component(dependencies = [NetworkClientProvider::class,ChatDatabaseProvider::class, AppProvider::class])
interface FacadeComponent: ProvidersFacade {

        companion object {
        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .chatDatabaseProvider(CoreProvidersFactory.createChatDatabaseComponent(AppComponent.create(application)))
                .networkClientProvider(CoreProvidersFactory.createNetworkClient(AppComponent.create(application)))
                .build()
    }
}