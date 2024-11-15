package com.compose.data.datamapper

import com.common.graphql.GetCharactersQuery
import com.compose.domain.mapper.CharacterModel

fun getCharactersQueryToCharacterModel(results : GetCharactersQuery.Result?) : CharacterModel {
    return CharacterModel(
        id = results?.character?.id.orEmpty(),
        name = results?.character?.name.orEmpty(),
        image = results?.character?.image.orEmpty(),
        gender = results?.character?.gender.orEmpty(),
        species = results?.character?.species.orEmpty(),
        status = results?.character?.status.orEmpty()
    )
}