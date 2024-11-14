package com.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.domain.usecase.GetCharactersUseCase
import com.compose.presentation.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val characterUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _charactersState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val charactersState = _charactersState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.getCharacters().catch {
                _charactersState.emit(UiState.Error(it.message))
            }.collect { result ->
                _charactersState.emit(UiState.Success(result))
            }
        }
    }
}