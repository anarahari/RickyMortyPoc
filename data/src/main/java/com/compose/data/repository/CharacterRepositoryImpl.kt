package com.compose.data.repository

import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetCharactersQuery
import com.compose.data.datamapper.getCharactersQueryToCharacterModel
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.repository.CharacterRepository
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    CharacterRepository {

    override suspend fun getCharacters(): List<CharacterModel?> {
        return try {
            val response = apolloClient.query(GetCharactersQuery()).execute()
            val results = response.data?.characters?.results?.map {
                getCharactersQueryToCharacterModel(it)
            } ?: emptyList()
            results
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override suspend fun getCharacterDetails(id: String): CharacterModel? {
        TODO("Not yet implemented")
    }
}
