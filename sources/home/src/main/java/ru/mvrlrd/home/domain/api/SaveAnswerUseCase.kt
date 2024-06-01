package ru.mvrlrd.home.domain.api

import ru.mvrlrd.core_api.database.entity.Answer

interface SaveAnswerUseCase {
    suspend operator fun invoke(answer: Answer)
}