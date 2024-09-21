package ru.mvrlrd.main

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.featureapi.FeatureApi
import ru.mvrlrd.featureapi.FeatureApiProvider


@Component(dependencies = [ProvidersFacade::class, FeatureApiProvider::class])
interface MainComponent {
    fun provideFeatureMap(): Map<String, @JvmSuppressWildcards FeatureApi>
    fun inject(mainActivity: MainActivity)

    companion object{
        fun create(providersFacade: ProvidersFacade): MainComponent =
            DaggerMainComponent.builder()
                .providersFacade(providersFacade)
                .featureApiProvider(providersFacade as FeatureApiProvider)
                .build()
    }
}