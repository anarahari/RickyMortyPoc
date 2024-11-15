package com.compose.domain.repository

import com.compose.domain.mapper.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacters(): Flow<List<CharacterModel>>
}
