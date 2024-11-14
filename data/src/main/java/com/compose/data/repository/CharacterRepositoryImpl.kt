package com.compose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetCharactersQuery
import com.compose.domain.paging.CharacterPagingSource
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) : CharacterRepository {
    override suspend fun getCharacters(): Flow<PagingData<GetCharactersQuery.Result>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                CharacterPagingSource(
                    apolloClient
                )
            },
        ).flow
}
