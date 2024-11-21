package com.compose.domain.usecase

import com.compose.domain.mapper.CharacterModel
import com.compose.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun invoke(): List<CharacterModel?> {
        return characterRepository.getCharacters()
    }
}
