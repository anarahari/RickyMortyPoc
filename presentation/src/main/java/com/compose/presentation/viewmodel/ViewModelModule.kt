package com.compose.presentation.viewmodel

import com.compose.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        getCharactersUseCase: GetCharactersUseCase,
        ): ViewModelFactoryProvider {
        return ViewModelFactoryProvider(getCharactersUseCase)
    }
}