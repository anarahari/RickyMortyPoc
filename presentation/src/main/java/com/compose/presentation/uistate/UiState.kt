package com.compose.presentation.uistate

sealed class UiState {
    data object Loading : UiState()

    data class Success< T>(
        val data: T,
    ) : UiState()

    data class Error(
        val msg: String?,
    ) : UiState()
}