package com.compose.domain.usecase

import androidx.paging.PagingData
import com.compose.domain.repository.CharacterRepository
import com.data.graphql.GetCharactersQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharacters(): Flow<PagingData<GetCharactersQuery.Result>> {
        return characterRepository.getCharacters()
    }
}
