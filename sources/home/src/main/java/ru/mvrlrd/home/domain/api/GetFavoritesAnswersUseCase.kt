package ru.mvrlrd.home.domain.api

import ru.mvrlrd.core_api.database.answer.entity.Answer

interface GetFavoritesAnswersUseCase {
    suspend operator fun invoke(): List<Answer>
}