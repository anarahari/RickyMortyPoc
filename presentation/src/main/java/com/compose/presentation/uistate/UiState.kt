package com.compose.presentation.uistate

import androidx.paging.PagingData
import com.common.graphql.GetCharactersQuery

sealed class UiState {
    data object Empty : UiState()

    data object Loading : UiState()

    data class Success(
        val data: PagingData<GetCharactersQuery.Result>,
    ) : UiState()

    data class Error(
        val msg: String?,
    ) : UiState()
}