package com.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compose.common.module.IoDispatcher
import com.compose.domain.usecase.GetCharacterDetailsUseCase
import com.compose.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ViewModelFactoryProvider
@Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterViewModel::class.java) -> {
                CharacterViewModel(
                    getCharactersUseCase,
                    coroutineDispatcher
                ) as T
            }
            modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java) -> {
                CharacterDetailsViewModel(
                    getCharacterDetailsUseCase,
                    coroutineDispatcher
                ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
