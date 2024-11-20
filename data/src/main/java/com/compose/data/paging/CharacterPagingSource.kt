package com.compose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetCharactersQuery

/**
 * Pagination data to get results
 * Useful for future development
 * **/
class CharacterPagingSource(
    private val apolloClient: ApolloClient,
) : PagingSource<Int, GetCharactersQuery.Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharactersQuery.Result> {
        val page = params.key ?: 1

        return try {
            val response = apolloClient.query(GetCharactersQuery()).execute()
            // Check for errors in the response
            if (response.hasErrors()) {
                LoadResult.Error(Exception(response.errors?.joinToString(", ") { it.message }))
            } else {
                val characters = response.data?.characters?.results?.filterNotNull() ?: emptyList()
                val nextKey = page + 1

                LoadResult.Page(
                    data = characters,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextKey, // Convert nextKey to Optional
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetCharactersQuery.Result>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
