package com.compose.domain.mapper

data class Character(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val status: String? = null,
    val created: String? = null,
    val origin: Origin = Origin(),
    val location: Location = Location(),
    val episodes: List<Episode>? = emptyList(),
)

data class Origin(
    val name: String? = null,
    val dimension: String? = null,
)

data class Location(
    val id: String? = null,
    val name: String? = null,
    val dimension: String? = null
)

data class Episode(
    val id: String? = null,
    val name: String? = null,
    val airDate: String? = null
)
