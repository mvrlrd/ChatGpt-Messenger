package ru.mvrlrd.home.domain

import ru.mvrlrd.core_api.database.AnswersDao
import ru.mvrlrd.core_api.database.entity.Answer
import ru.mvrlrd.home.domain.api.SaveAnswerUseCase
import javax.inject.Inject

class SaveAnswerUseCaseImpl @Inject constructor(private val dao: AnswersDao): SaveAnswerUseCase {
    override suspend fun invoke(answer: Answer): Long {
       return dao.createAnswer(answer)
    }
}