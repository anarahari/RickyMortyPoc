package com.compose.domain.usecase

import com.compose.domain.mapper.CharacterModel
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun invoke(): Flow<List<CharacterModel>> {
        return characterRepository.getCharacters()
    }
}
