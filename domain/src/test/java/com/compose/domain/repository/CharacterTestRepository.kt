package com.compose.domain.repository

import com.compose.domain.mapper.Character

class CharacterTestRepository : CharacterRepository {

    override suspend fun getCharacters(): List<Character?> {
        val characters = listOf(
            Character(
                id = "1",
                name = "Arjun Narahari",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            Character(
                id = "2",
                name = "Chandbaba Shaik",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            Character(
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

    override suspend fun getCharacterDetails(id: String): Character {
        TODO()
    }
}
