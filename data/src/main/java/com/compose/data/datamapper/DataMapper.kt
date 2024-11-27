package com.compose.data.datamapper

import com.common.graphql.GetCharacterDetailsQuery
import com.common.graphql.GetCharactersQuery
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.mapper.EpisodeModel
import com.compose.domain.mapper.LocationModel
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
            location = it.character?.location?.toLocationModel()!!,
            episodes = mapToEpisodeModel(it.character?.episode),
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

fun GetCharacterDetailsQuery.Location.toLocationModel(): LocationModel {
    return LocationModel(
        name = this.name.orEmpty(),
        dimension = this.dimension.orEmpty(),
        id = this.id.orEmpty()
    )
}

fun mapToEpisodeModel(episodes: List<GetCharacterDetailsQuery.Episode?>?): List<EpisodeModel> {
    val list = mutableListOf<EpisodeModel>()
    episodes?.let {
        for (episode in it) {
            list.add(
                EpisodeModel(
                    name = episode?.name.orEmpty(),
                    airDate = episode?.air_date.orEmpty(),
                    episode = episode?.episode.orEmpty(),
                    created = episode?.created.orEmpty(),
                    id = episode?.id.orEmpty()
                )
            )
        }
    }
    return list
}
