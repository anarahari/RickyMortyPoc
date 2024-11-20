package com.compose.domain.repository

import com.compose.domain.mapper.CharacterModel

class CharacterTestRepository : CharacterRepository {
    override suspend fun getCharacters(): List<CharacterModel?> {
        val characters = listOf(
            CharacterModel(
                id = "1",
                name = "Arjun Narahari",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            CharacterModel(
                id = "2",
                name = "Chandbaba Shaik",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            CharacterModel(
                id = "3",
                name = "Rick Morty",
                image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            )
        )
        return characters
    }
}
