package ru.mvrlrd.companion

import androidx.compose.ui.graphics.Color

sealed interface UiState {
    object Loading : UiState


    object Home : UiState
    object Error : UiState
    data class Success(val data: List<Color>) : UiState

    enum class LoadingType {
        INITIAL_LOAD, PULL_REFRESH,
    }
}