package ru.mvrlrd.feature_home.domain.api

interface RemoveChatUseCase {
    suspend operator fun invoke(id: Long)
}