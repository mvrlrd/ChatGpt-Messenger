package ru.mvrlrd.home.domain.api

interface DeleteMessageUseCase {
    suspend operator fun invoke(messageId: Long)
}