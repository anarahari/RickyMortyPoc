package com.compose.presentation

import app.cash.turbine.test
import com.compose.common.Resource
import com.compose.domain.mapper.Location
import com.compose.domain.mapper.Origin
import com.compose.domain.usecase.GetCharacterDetailsUseCase
import com.compose.presentation.uistate.UiState
import com.compose.presentation.viewmodel.CharacterDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
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
class CharacterDetailsViewModelTest {

    private lateinit var SUT: CharacterDetailsViewModel

    @RelaxedMockK
    private lateinit var characterDetailsUseCase: GetCharacterDetailsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        SUT = CharacterDetailsViewModel(characterDetailsUseCase, testDispatcher)
    }

    @Test
    fun `when fetching character details then check if the initial character details state is in loading state`() =
        runTest(testDispatcher) {
            val currentState = SUT.characterDetailsState.value
            assertEquals(UiState(isLoading = true), currentState)
            assertTrue(SUT.characterDetailsState.value is UiState)
        }

    @Test
    fun `when invoke get character details should emit success`() = runTest(testDispatcher) {
        // Given
        val characterId = "1"
        val characterDetails = com.compose.domain.mapper.Character(
            id = "1",
            name = "Rick",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = "male",
            species = "Human",
            status = "Alive",
            created = "2017-11-18T19:33:01.173Z",
            origin = Origin(name = "Earth", dimension = "Replacement Dimension"),
            location = Location(id = "1", name = "Earth", dimension = "Dimension"),
            episodes = listOf(
                com.compose.domain.mapper.Episode("6", "Rick Potion #9", "January 27, 2014"),
                com.compose.domain.mapper.Episode("7", "Raising Gazorpazorp", "March 10, 2014")
            )
        )
        coEvery { characterDetailsUseCase.invoke(characterId) } returns flowOf(
            Resource.Success(characterDetails)
        )

        // when
        SUT.getCharacterDetails(characterId)

        // then
        SUT.characterDetailsState.test {
            assertTrue(awaitItem() is UiState)
            assertEquals(characterDetails, (awaitItem() as UiState).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when invoke get character details with wrong character id should emit failure`() = runTest(testDispatcher) {
        // Given
        val characterId = "-1"
        val exception = RuntimeException("Error fetching character details")
        coEvery { characterDetailsUseCase.invoke(characterId) } returns flow {
            emit(Resource.Error(exception.message.toString()))
        }
        // when
        SUT.getCharacterDetails(characterId)

        // then
        SUT.characterDetailsState.test {
            assertTrue(awaitItem() is UiState)
            assertEquals(UiState(error = exception.message.toString()), awaitItem())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
