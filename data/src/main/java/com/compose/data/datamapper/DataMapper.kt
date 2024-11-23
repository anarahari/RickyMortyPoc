package com.compose.data.datamapper

import com.common.graphql.GetCharacterDetailsQuery
import com.common.graphql.GetCharactersQuery
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.mapper.EpisodeModel
import com.compose.domain.mapper.OriginModel

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

fun getCharacterDetailsQueryToCharacterModel(data: GetCharacterDetailsQuery.Data?): CharacterModel? {
    return data?.let {
        CharacterModel(
            id = it.character?.id.orEmpty(),
            name = it.character?.name.orEmpty(),
            image = it.character?.image.orEmpty(),
            gender = it.character?.gender.orEmpty(),
            species = it.character?.species.orEmpty(),
            status = it.character?.status.orEmpty(),
            origin = it.character?.origin?.toOriginModel()!!,
            episodes = it.character?.episode?.mapNotNull { episodes -> episodes?.toEpisodeModel() }
        )
    }
}

fun GetCharacterDetailsQuery.Origin.toOriginModel(): OriginModel {
    return OriginModel(
        name = this.name.orEmpty(),
        type = this.type.orEmpty(),
        dimension = this.dimension.orEmpty(),
        created = this.created.orEmpty(),
        id = this.id.orEmpty()
    )
}

fun GetCharacterDetailsQuery.Episode.toEpisodeModel(): List<EpisodeModel> {
    return listOf(
        EpisodeModel(
            name = this.name.orEmpty(),
            airDate = this.air_date.orEmpty(),
            episode = this.episode.orEmpty(),
            created = this.created.orEmpty(),
            id = this.id.orEmpty()
        )
    )
}

