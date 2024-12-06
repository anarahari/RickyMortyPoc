package com.compose.domain.usecase

import com.compose.domain.mapper.Character
import com.compose.domain.mapper.Episode
import com.compose.domain.repository.EpisodeRepository
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
class GetEpisodeDetailsUseCaseTest {

    private lateinit var SUT: GetEpisodeDetailsUseCase

    @RelaxedMockK
    private lateinit var repository: EpisodeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        SUT = GetEpisodeDetailsUseCase(repository)
    }

    @Test
    fun `get episode details on success`() = runTest {
        val episodeId = "1"
        val episode = Episode(
            id = "1",
            name = "Pilot",
            airDate = "December 2, 2013",
            characters = listOf(
                Character(
                    id = "1",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                ),
                Character(
                    id = "2",
                    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
                ),
                Character(
                    id = "3",
                    image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
                ),
                Character(
                    id = "4",
                    image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg"
                )
            )
        )

        coEvery { repository.getEpisodeDetails("1") } returns episode
        val result = SUT.invoke(episodeId)
        assertEquals(episode, result.last().data)
    }

    @Test
    fun `when fetching episode details for wrong episode id show error`() = runTest {
        val episodeId = "-1"
        val exception = RuntimeException("Error fetching episode details")
        coEvery { repository.getEpisodeDetails(episodeId) } throws exception
        val result = SUT.invoke(episodeId)
        assertEquals("Error fetching episode details", result.last().message)
    }
}
