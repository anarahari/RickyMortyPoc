package com.compose.data.datamapper

import com.common.graphql.GetCharacterDetailsQuery
import com.common.graphql.GetCharactersQuery
import com.compose.domain.mapper.Character
import com.compose.domain.mapper.Episode
import com.compose.domain.mapper.Location
import com.compose.domain.mapper.Origin

fun getCharactersQueryToCharacterModel(results: GetCharactersQuery.Result?): Character? {
    return results?.let {
        Character(
            id = it.character.id,
            name = it.character.name,
            image = it.character.image,
            gender = it.character.gender,
            species = it.character.species,
            status = it.character.status
        )
    }
}

fun getCharacterDetailsQueryToCharacterModel(data: GetCharacterDetailsQuery.Data?): Character? {
    return data?.let {
        Character(
            id = it.character?.id,
            name = it.character?.name,
            image = it.character?.image,
            gender = it.character?.gender,
            species = it.character?.species,
            status = it.character?.status,
            origin = mapToOriginModel(it.character?.origin),
            location = mapToLocationModel(it.character?.location),
            episodes = mapToEpisodeModel(it.character?.episode),
        )
    }
}

fun mapToOriginModel(origin: GetCharacterDetailsQuery.Origin?): Origin {
    return Origin(
        name = origin?.name,
        dimension = origin?.dimension
    )
}

fun mapToLocationModel(location: GetCharacterDetailsQuery.Location?): Location {
    return Location(
        name = location?.name,
        dimension = location?.dimension,
        id = location?.id
    )
}

fun mapToEpisodeModel(episodes: List<GetCharacterDetailsQuery.Episode?>?): List<Episode>? {
    return episodes?.map { episode ->
        Episode(
            id = episode?.id,
            name = episode?.name,
            airDate = episode?.air_date
        )
    }
}
