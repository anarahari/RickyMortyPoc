package com.compose.domain.repository

import com.compose.domain.mapper.Character

interface CharacterRepository {

    suspend fun getCharacters(): List<Character?>
    suspend fun getCharacterDetails(id: String): Character?
}
