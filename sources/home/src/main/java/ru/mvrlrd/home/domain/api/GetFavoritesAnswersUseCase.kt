package ru.mvrlrd.home.domain.api

import ru.mvrlrd.core_api.database.entity.Answer

interface GetFavoritesAnswersUseCase {
    suspend operator fun invoke(): List<Answer>
}