package com.compose.data.repository

import com.apollographql.apollo.ApolloClient
import com.common.graphql.GetCharacterDetailsQuery
import com.common.graphql.GetCharactersQuery
import com.compose.common.R
import com.compose.common.Resource
import com.compose.data.datamapper.getCharacterDetailsQueryToCharacterModel
import com.compose.data.datamapper.getCharactersQueryToCharacterModel
import com.compose.domain.mapper.Character
import com.compose.domain.repository.CharacterRepository
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    CharacterRepository {

    override suspend fun getCharacters(): Resource<List<Character?>> {
        return try {
            val response = apolloClient.query(GetCharactersQuery()).execute()
            if (response.hasErrors()) {
                Resource.Error(
                    IllegalStateException(response.errors?.joinToString { it.message }).toString(),
                    R.string.resp_failure
                )
            }
            val results = response.data?.characters?.results?.map {
                getCharactersQueryToCharacterModel(it)
            } ?: emptyList()
            Resource.Success(data = results)
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }

    override suspend fun getCharacterDetails(id: String): Resource<Character?> {
        return try {
            val response = apolloClient.query(GetCharacterDetailsQuery(id)).execute()
            if (response.hasErrors()) {
                Resource.Error(
                    IllegalStateException(response.errors?.joinToString { it.message }).toString(),
                    R.string.resp_failure
                )
            }
            Resource.Success(data = getCharacterDetailsQueryToCharacterModel(response.data))
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
}
