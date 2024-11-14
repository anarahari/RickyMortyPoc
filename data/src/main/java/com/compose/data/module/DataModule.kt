package com.compose.data.module

import com.apollographql.apollo.ApolloClient
import com.compose.data.repository.CharacterRepositoryImpl
import com.compose.domain.repository.CharacterRepository
import com.compose.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    @Provides
    @Singleton
    fun provideCharacterModule(apolloClient: ApolloClient): CharacterRepository {
        return CharacterRepositoryImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideCharacterCase(repository: CharacterRepository): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }
}
