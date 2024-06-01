package ru.mvrlrd.home.di

import dagger.Binds
import dagger.Module
import ru.mvrlrd.home.domain.GetFavoritesAnswersUseCaseImpl
import ru.mvrlrd.home.domain.SaveAnswerUseCaseImpl
import ru.mvrlrd.home.domain.api.GetFavoritesAnswersUseCase
import ru.mvrlrd.home.domain.api.SaveAnswerUseCase

@Module
interface HomeModule {
    @Binds
    fun bindsaveAnswerUseCase(saveAnswerUseCaseImpl: SaveAnswerUseCaseImpl): SaveAnswerUseCase

    @Binds
    fun bindFavoritesAnswersUseCase(getFavoritesAnswersUseCaseImpl: GetFavoritesAnswersUseCaseImpl): GetFavoritesAnswersUseCase
}