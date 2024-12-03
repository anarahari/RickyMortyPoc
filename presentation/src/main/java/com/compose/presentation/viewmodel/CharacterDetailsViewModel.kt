package com.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.common.Resource
import com.compose.common.module.IoDispatcher
import com.compose.domain.usecase.GetCharacterDetailsUseCase
import com.compose.presentation.uistate.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUseCase: GetCharacterDetailsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterDetailsState: MutableStateFlow<UiState> = MutableStateFlow(UiState(isLoading = true))
    val characterDetailsState: StateFlow<UiState> = _characterDetailsState.asStateFlow()

    /**
     * Get character details by passing character id
     * **/
    fun getCharacterDetails(characterId: String) {
        viewModelScope.launch(coroutineDispatcher) {
            characterDetailsUseCase.invoke(characterId).catch {
                _characterDetailsState.emit(UiState(error = it.message.toString()))
            }.collect { result ->
                when (result) {
                    is Resource.Loading<*> -> {
                        _characterDetailsState.emit(UiState(isLoading = true))
                    }

                    is Resource.Success<*> -> {
                        _characterDetailsState.emit(UiState(data = result.data))
                    }

                    is Resource.Error<*> -> {
                        _characterDetailsState.emit(UiState(error = result.message.toString()))
                    }
                }
            }
        }
    }
}
