package com.compose.domain.repository

import com.compose.domain.mapper.Episode

interface EpisodeRepository {

    suspend fun getEpisodeDetails(id: String): Episode?
}
