package com.compose.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.compose.domain.mapper.CharacterModel
import com.compose.presentation.R
import com.compose.presentation.uistate.UiState
import com.compose.presentation.viewmodel.CharacterViewModel

@Composable
fun CharacterListScreen(viewModel: CharacterViewModel, modifier: Modifier = Modifier) {
    val characterState by viewModel.charactersState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CustomAppBar(stringResource(R.string.all_characters), null)

        when (characterState) {
            is UiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }

            is UiState.Error -> {
                (characterState as UiState.Error).msg?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                    )
                }
            }

            is UiState.Success<*> -> {
                val charactersList = remember { (characterState as UiState.Success<*>).data }
                CharactersList(charactersList as List<CharacterModel>)
            }
        }
    }
}

@Composable
private fun CharactersList(characterList: List<CharacterModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characterList.size, key = { characterList[it].id }) { index ->
            CharacterItem(character = characterList[index])
        }
    }
}

@Composable
private fun CharacterItem(character: CharacterModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
            Text(
                text = character.name,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }
}