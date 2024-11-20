package com.compose.domain.repository

import com.compose.domain.mapper.CharacterModel

interface CharacterRepository {

    suspend fun getCharacters(): List<CharacterModel?>
}
