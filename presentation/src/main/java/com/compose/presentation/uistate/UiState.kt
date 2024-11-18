package com.compose.presentation.uistate

data class UiState(
    var isLoading: Boolean = false,
    val data: Any? = null,
    val error: String = ""
)