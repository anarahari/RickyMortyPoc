package com.compose.domain.mapper

data class CharacterModel(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val status: String? = null,
    val created: String? = null,
    val origin: OriginModel = OriginModel(),
    val location: LocationModel = LocationModel(),
    val episodes: List<EpisodeModel>? = emptyList(),
)

data class OriginModel(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val created: String? = null,
)

data class LocationModel(
    val id: String? = null,
    val name: String? = null,
    val dimension: String? = null
)

data class EpisodeModel(
    val id: String,
    val name: String,
    val airDate: String,
    val episode: String,
    val created: String,
)
