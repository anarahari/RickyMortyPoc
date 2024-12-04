package com.compose.domain.usecase

import com.compose.common.Resource
import com.compose.domain.mapper.Character
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    fun invoke(): Flow<Resource<List<Character?>>> = flow {
        emit(Resource.Loading())
        val characters = characterRepository.getCharacters()
        emit(Resource.Success(characters))
    }.catch {
        emit(Resource.Error(it.message ?: "An Unexpected error occurred"))
    }
}
