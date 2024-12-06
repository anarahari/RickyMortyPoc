package com.compose.presentation.viewmodel

import com.compose.common.module.IoDispatcher
import com.compose.domain.usecase.GetCharacterDetailsUseCase
import com.compose.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        getCharactersUseCase: GetCharactersUseCase,
        getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): ViewModelFactoryProvider {
        return ViewModelFactoryProvider(
            getCharactersUseCase,
            getCharacterDetailsUseCase,
            coroutineDispatcher
        )
    }
}
