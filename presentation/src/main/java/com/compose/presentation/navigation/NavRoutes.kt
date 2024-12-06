package com.compose.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object RouteCharacterList

@Serializable
data class RouteCharacterDetails(val characterId: String)
