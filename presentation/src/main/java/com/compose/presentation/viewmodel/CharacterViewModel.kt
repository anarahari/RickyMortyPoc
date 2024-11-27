package com.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.common.Resource
import com.compose.common.module.IoDispatcher
import com.compose.domain.usecase.GetCharacterDetailsUseCase
import com.compose.domain.usecase.GetCharactersUseCase
import com.compose.presentation.uistate.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val characterUseCase: GetCharactersUseCase,
    private val characterDetailsUseCase: GetCharacterDetailsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _charactersState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val charactersState: StateFlow<UiState> = _charactersState.asStateFlow()

    private val _characterDetailsState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val characterDetailsState: StateFlow<UiState> = _characterDetailsState.asStateFlow()

//    init {
//        getCharacters()
//    }

    /**
     * Get List of characters
     * **/
    fun getCharacters() {
        viewModelScope.launch(coroutineDispatcher) {
            characterUseCase.invoke().catch {
                _charactersState.emit(UiState(error = it.message.toString()))
            }.collect { result ->
                when (result) {
                    is Resource.Loading<*> -> {
                        _charactersState.emit(UiState(isLoading = true))
                    }

                    is Resource.Success<*> -> {
                        _charactersState.emit(UiState(data = result.data))
                    }

                    is Resource.Error<*> -> {
                        _charactersState.emit(UiState(error = result.message.toString()))
                    }
                }
            }
        }
    }

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
