package ru.mvrlrd.main

import ru.mvrlrd.core_api.database.entity.Answer

sealed interface UiState {
    object Loading : UiState
    object Initial : UiState
    object Error : UiState
    data class Success(val list: List<Answer>) : UiState
    enum class LoadingType {
        INITIAL_LOAD, PULL_REFRESH,
    }
}