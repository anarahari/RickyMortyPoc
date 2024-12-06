package com.compose.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.compose.domain.mapper.Character
import com.compose.presentation.uistate.UiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CharacterListScreen(
    characters: StateFlow<UiState>,
    toolbarTitle: String,
    modifier: Modifier = Modifier,
    onNavigateCharacterDetails: (String) -> Unit
) {
    val characterState by characters.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CustomAppBar(toolbarTitle, null)

        if (characterState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }

        if (characterState.error.isNotEmpty()) {
            Text(
                text = characterState.error.toString(),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
            )
        }

        characterState.data?.let {
            val charactersList = remember { characterState.data }
            CharactersList(charactersList as List<Character>, onNavigateCharacterDetails)
        }
    }
}

@Composable
private fun CharactersList(
    characterList: List<Character>,
    onNavigateCharacterDetails: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characterList.size, key = { characterList[it].id.toString() }) { index ->
            CharacterItem(character = characterList[index], onNavigateCharacterDetails)
        }
    }
}

@Composable
private fun CharacterItem(character: Character, onNavigateCharacterDetails: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onNavigateCharacterDetails(character.id.orEmpty())
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
            Text(
                text = character.name.orEmpty(),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }
}
