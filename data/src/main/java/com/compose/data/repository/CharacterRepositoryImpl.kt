package com.compose.data.repository

import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetCharactersQuery
import com.compose.data.datamapper.getCharactersQueryToCharacterModel
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    CharacterRepository {
    override suspend fun getCharacters(): Flow<List<CharacterModel>> = flow {
        val response = apolloClient.query(GetCharactersQuery()).execute()
        if (response.hasErrors()) {
            emit(emptyList())
            return@flow
        }
        val results = response.data?.characters?.results?.map {
            getCharactersQueryToCharacterModel(it)
        } ?: emptyList()
        emit(results)
    }.catch {
        emit(emptyList())
    }
}
