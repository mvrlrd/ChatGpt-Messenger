package ru.mvrlrd.favorites.domain.api

interface RemoveChatUseCase {
    suspend operator fun invoke(id: Long)
}