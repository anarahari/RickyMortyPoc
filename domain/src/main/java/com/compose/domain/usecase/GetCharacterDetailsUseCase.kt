package com.compose.domain.usecase

import com.compose.common.Resource
import com.compose.domain.mapper.Character
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    fun invoke(id: String): Flow<Resource<Character?>> = flow {
        emit(Resource.Loading())
        val characters = characterRepository.getCharacterDetails(id)
        emit(Resource.Success(characters))
    }.catch {
        emit(Resource.Error(it.message ?: "An Unexpected error occurred"))
    }
}
