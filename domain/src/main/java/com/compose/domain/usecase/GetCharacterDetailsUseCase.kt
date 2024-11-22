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

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    fun invoke(id : String): Flow<Resource<CharacterModel?>> = flow {
        emit(Resource.Loading())
        val characters = characterRepository.getCharacterDetails(id)
        emit(Resource.Success(characters))
    }.catch {
        emit(Resource.Error(it.message ?: "An Unexpected error occurred"))
    }.flowOn(coroutineDispatcher)
}