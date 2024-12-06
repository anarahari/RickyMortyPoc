package com.compose.domain.usecase

import com.compose.common.Resource
import com.compose.domain.mapper.Character
import com.compose.domain.mapper.Episode
import com.compose.domain.mapper.Location
import com.compose.domain.mapper.Origin
import com.compose.domain.repository.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharacterDetailsUseCaseTest {

    private lateinit var SUT: GetCharacterDetailsUseCase
    @RelaxedMockK
    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        SUT = GetCharacterDetailsUseCase(repository)
    }

    @Test
    fun `get character details on success`() = runTest() {
        val character = Character(
            id = "1",
            name = "Rick Sanchez",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = "Male",
            origin = Origin(name = "Planet", dimension = "Dimension C-137"),
            species = "Human",
            status = "Alive",
            episodes = listOf(
                Episode(id = "1", name = "Pilot", airDate = "December 2, 2013"),
                Episode(id = "2", name = "Lawnmower", airDate = "December 9, 2013")
            ),
            location = Location(id = "3", name = "Citadel of Ricks", dimension = "unknown")
        )

        val characterId = "1"
        coEvery { repository.getCharacterDetails("1") } returns Resource.Success(character)
        val result = SUT.invoke(characterId)
        assertEquals(character, result.last().data)
    }

    @Test
    fun `when fetching characters details for wrong character id show error`() =
        runTest() {
            val characterId = "-1"
            val exception = RuntimeException("Error fetching character details")
            coEvery { repository.getCharacterDetails(characterId) } throws exception
            val result = SUT.invoke(characterId)
            assertEquals("Error fetching character details", result.last().message)
        }
}
