package com.compose.domain.usecase

import com.compose.common.Resource
import com.compose.domain.mapper.Episode
import com.compose.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    fun invoke(id: String): Flow<Resource<Episode?>> = flow {
        emit(Resource.Loading())
        val episodeDetails = episodeRepository.getEpisodeDetails(id)
        emit(Resource.Success(episodeDetails))
    }.catch {
        emit(Resource.Error(it.message ?: "An Unexpected error occurred"))
    }
}
