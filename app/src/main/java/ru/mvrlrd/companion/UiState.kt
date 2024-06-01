package ru.mvrlrd.companion

import androidx.compose.ui.graphics.Color

sealed interface UiState {
    object Loading : UiState

object Initial: UiState
    object Error : UiState
    object Success : UiState

    enum class LoadingType {
        INITIAL_LOAD, PULL_REFRESH,
    }
}