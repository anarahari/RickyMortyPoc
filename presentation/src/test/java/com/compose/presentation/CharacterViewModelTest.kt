package com.compose.presentation

import app.cash.turbine.test
import com.compose.common.Resource
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.usecase.GetCharactersUseCase
import com.compose.presentation.uistate.UiState
import com.compose.presentation.viewmodel.CharacterViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {
    private lateinit var viewModel: CharacterViewModel
    private lateinit var charactersUseCase: GetCharactersUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        charactersUseCase = mockk(relaxed = true)
        viewModel = CharacterViewModel(charactersUseCase, testDispatcher)
    }

    @Test
    fun `while fetching characters list check if the charactersState is in loading state`() =
        runTest(testDispatcher) {
            val currentState = viewModel.charactersState.value
            assertEquals(UiState(isLoading = true), currentState)
            assertTrue(viewModel.charactersState.value is UiState)
        }

    @Test
    fun `get list of characters on success`() = runTest(testDispatcher) {
        val characters = listOf(
            CharacterModel(
                id = "1",
                name = "Arjun Narahari",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            CharacterModel(
                id = "2",
                name = "Chandbaba Shaik",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            ),
            CharacterModel(
                id = "3",
                name = "Rick Morty",
                image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                gender = "Male",
                species = "Human",
                status = "Alive"
            )
        )
        coEvery { charactersUseCase.getCharacters() } returns flowOf(
            Resource.Success(characters)
        )

        viewModel.getCharacters()
        viewModel.charactersState.test {
            assertTrue(awaitItem() is UiState)
            assertEquals(characters, (awaitItem() as UiState).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `return characters list to give error`() = runTest(testDispatcher) {
        val exception = RuntimeException("Error fetching character results")
        coEvery { charactersUseCase.getCharacters() } returns flow {
            emit(Resource.Error(exception.message.toString()))
        }

        viewModel.getCharacters()

        viewModel.charactersState.test {
            assertTrue(awaitItem() is UiState)
            assertEquals(UiState(error = exception.message.toString()), awaitItem())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
