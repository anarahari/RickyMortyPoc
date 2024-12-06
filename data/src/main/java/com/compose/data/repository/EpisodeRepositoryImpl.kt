package com.compose.data.repository

import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetEpisodeDetailsQuery
import com.compose.common.R
import com.compose.common.Resource
import com.compose.data.datamapper.getEpisodeDetailsQueryToEpisodeModel
import com.compose.domain.mapper.Episode
import com.compose.domain.repository.EpisodeRepository
import javax.inject.Inject

internal class EpisodeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : EpisodeRepository {
    override suspend fun getEpisodeDetails(id: String): Resource<Episode?> {
        return try {
            val response = apolloClient.query(GetEpisodeDetailsQuery(id)).execute()
            if (response.hasErrors()) {
                Resource.Error(
                    IllegalStateException(response.errors?.joinToString { it.message }).toString(),
                    R.string.resp_failure
                )
            }
            Resource.Success(data = getEpisodeDetailsQueryToEpisodeModel(response.data?.episode))
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
}
