package com.compose.domain.repository

import androidx.paging.PagingData
import com.data.graphql.GetCharactersQuery
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacters(): Flow<PagingData<GetCharactersQuery.Result>>
}
