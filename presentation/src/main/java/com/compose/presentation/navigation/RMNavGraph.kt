package com.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.compose.presentation.R
import com.compose.presentation.screens.CharacterListScreen
import com.compose.presentation.viewmodel.CharacterViewModel

@Composable
fun RMNavGraph(characterViewModel: CharacterViewModel){
    val navController = rememberNavController()
    val onNavigateCharacterDetails: (String) -> Unit = { characterId: String -> navController.navigate(RouteCharacterDetails(characterId = characterId)) }

    NavHost(navController = navController, startDestination = RouteCharacterList){
        composable<RouteCharacterList> {
            CharacterListScreen(characterViewModel, stringResource(R.string.all_characters))
        }
        composable<RouteCharacterDetails> { navBackStackEntry ->
            val characterDetails : RouteCharacterDetails = navBackStackEntry.toRoute()

        }
    }
}