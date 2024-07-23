package ru.mvrlrd.home.domain

import ru.mvrlrd.core_api.database.answer.AnswersDao
import ru.mvrlrd.core_api.database.answer.entity.Answer
import ru.mvrlrd.home.domain.api.GetFavoritesAnswersUseCase
import javax.inject.Inject

class GetFavoritesAnswersUseCaseImpl@Inject constructor(private val dao: AnswersDao): GetFavoritesAnswersUseCase {
    override suspend fun invoke(): List<Answer> {
        return dao.getAnswers()
    }
}