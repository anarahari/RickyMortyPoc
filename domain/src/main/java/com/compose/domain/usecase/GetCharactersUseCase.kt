package com.compose.domain.usecase

import com.compose.common.Resource
import com.compose.common.module.IoDispatcher
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getCharacters(): Flow<Resource<List<CharacterModel?>>> = flow {
        emit(Resource.Loading())
        val characters = characterRepository.getCharacters()
        emit(Resource.Success(characters))
    }.catch {
        emit(Resource.Error(it.message.toString() ?: "An Unexpected error occurred"))
    }.flowOn(ioDispatcher)
}
