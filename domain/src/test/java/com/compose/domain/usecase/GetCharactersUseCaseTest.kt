package com.compose.domain.usecase

import com.compose.domain.repository.CharacterTestRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {

    private lateinit var charactersUseCase: GetCharactersUseCase
    private val testDispatcher = StandardTestDispatcher()
    @RelaxedMockK
    private lateinit var repository: CharacterTestRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        charactersUseCase = GetCharactersUseCase(repository,testDispatcher)
    }

    @Test
    fun `get list of characters on success`() = runTest(testDispatcher) {
        val expectedCharacters = repository.getCharacters()
        val result = charactersUseCase.getCharacters()
        assertEquals(expectedCharacters, result.last().data)
    }

    @Test
    fun `get list of characters on failure`() = runTest(testDispatcher) {
        val exception = RuntimeException("Error fetching character results")
        coEvery { repository.getCharacters() } throws exception
        val result = charactersUseCase.getCharacters()
        assertEquals("Error fetching character results", result.last().message)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}