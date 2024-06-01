package ru.mvrlrd.home.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.core_api.network.RemoteRepository

import ru.mvrlrd.home.domain.api.GetFavoritesAnswersUseCase
import ru.mvrlrd.home.domain.api.SaveAnswerUseCase

@Component(
    dependencies = [ProvidersFacade::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun getRepo(): RemoteRepository
    fun getSaveUseCase(): SaveAnswerUseCase
    fun getGetFavsUseCase(): GetFavoritesAnswersUseCase

    companion object {
        fun create(providersFacade: ProvidersFacade): HomeComponent =
            DaggerHomeComponent.builder()
                .providersFacade(providersFacade)
                .build()
    }
}