package com.compose.domain.repository

import com.compose.common.Resource
import com.compose.domain.mapper.Episode

interface EpisodeRepository {

    suspend fun getEpisodeDetails(id: String): Resource<Episode?>
}
