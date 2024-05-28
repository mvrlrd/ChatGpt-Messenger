package ru.mvrlrd.companion

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade

@Component(dependencies = [ProvidersFacade::class])
interface MainComponent {

    fun inject(mainActivity: MainActivity)

    companion object{
        fun create(providersFacade: ProvidersFacade): MainComponent =
            DaggerMainComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}