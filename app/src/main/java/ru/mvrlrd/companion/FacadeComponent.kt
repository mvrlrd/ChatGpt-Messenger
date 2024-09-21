package ru.mvrlrd.companion

import android.app.Application
import dagger.Component
import ru.mvrlrd.companion.navigation.DaggerFeatureApiComponent
import ru.mvrlrd.companion.navigation.FeatureApiComponent
import ru.mvrlrd.core_api.database.chat.ChatDatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.NetworkClientProvider
import ru.mvrlrd.core_factory.CoreProvidersFactory
import ru.mvrlrd.featureapi.FeatureApiProvider

@Component(
    dependencies = [
        NetworkClientProvider::class,
        ChatDatabaseProvider::class,
        AppProvider::class,
        FeatureApiComponent::class
      ]
)
interface FacadeComponent : ProvidersFacade, FeatureApiProvider {
    companion object {
        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .featureApiComponent(DaggerFeatureApiComponent.create())
                .chatDatabaseProvider(CoreProvidersFactory.createChatDatabaseComponent(AppComponent.create(application)))
                .networkClientProvider(CoreProvidersFactory.createNetworkClient(AppComponent.create(application)))
                .build()
    }
}