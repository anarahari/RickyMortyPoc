package com.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.compose.presentation.R
import com.compose.presentation.screens.CharacterDetailsScreen
import com.compose.presentation.screens.CharacterListScreen
import com.compose.presentation.viewmodel.CharacterDetailsViewModel
import com.compose.presentation.viewmodel.CharacterViewModel

@Composable
fun RMNavGraph(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val onNavigateCharacterDetails: (String) -> Unit =
        { characterId: String -> navController.navigate(RouteCharacterDetails(characterId = characterId)) }

    NavHost(navController = navController, startDestination = RouteCharacterList) {
        composable<RouteCharacterList> {
            val characterViewModel: CharacterViewModel = viewModel(factory = viewModelFactory)
            characterViewModel.getCharacters()
            CharacterListScreen(
                characterViewModel.charactersState, stringResource(R.string.all_characters),
                onNavigateCharacterDetails = onNavigateCharacterDetails
            )
        }
        composable<RouteCharacterDetails> { navBackStackEntry ->
            val characterDetails: RouteCharacterDetails = navBackStackEntry.toRoute()
            val modifier: Modifier = Modifier
            val characterDetailsViewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
            characterDetailsViewModel.getCharacterDetails(characterDetails.characterId)
            CharacterDetailsScreen(
                modifier,
                characterDetailsViewModel.characterDetailsState, characterDetails.characterId,
                stringResource(R.string.character_details),
                onBackButtonPressed = navController::navigateUp
            )
        }
    }
}
