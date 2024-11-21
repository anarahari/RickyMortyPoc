package com.compose.data.datamapper

import com.common.graphql.GetCharactersQuery
import com.compose.domain.mapper.CharacterModel

fun getCharactersQueryToCharacterModel(results: GetCharactersQuery.Result?): CharacterModel? {
    return results?.let {
        CharacterModel(
            id = it.character.id.orEmpty(),
            name = it.character.name.orEmpty(),
            image = it.character.image.orEmpty(),
            gender = it.character.gender.orEmpty(),
            species = it.character.species.orEmpty(),
            status = it.character.status.orEmpty()
        )
    }
}
