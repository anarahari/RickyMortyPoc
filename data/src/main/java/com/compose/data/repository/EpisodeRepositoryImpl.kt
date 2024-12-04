package com.compose.data.repository

import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetEpisodeDetailsQuery
import com.compose.data.datamapper.getEpisodeDetailsQueryToEpisodeModel
import com.compose.domain.mapper.Episode
import com.compose.domain.repository.EpisodeRepository
import javax.inject.Inject

internal class EpisodeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : EpisodeRepository {
    override suspend fun getEpisodeDetails(id: String): Episode? {
        return try {
            val response = apolloClient.query(GetEpisodeDetailsQuery(id)).execute()
            getEpisodeDetailsQueryToEpisodeModel(response.data?.episode)
        } catch (ex: Exception) {
            null
        }
    }
}
