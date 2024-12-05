package com.compose.domain.repository

import com.compose.common.Resource
import com.compose.domain.mapper.Character

interface CharacterRepository {

    suspend fun getCharacters(): Resource<List<Character?>>
    suspend fun getCharacterDetails(id: String): Resource<Character?>
}
