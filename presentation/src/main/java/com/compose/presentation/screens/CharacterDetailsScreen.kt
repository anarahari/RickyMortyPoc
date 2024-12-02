package com.compose.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.compose.domain.mapper.Character
import com.compose.domain.mapper.Episode
import com.compose.presentation.R
import com.compose.presentation.ui.theme.dimens
import com.compose.presentation.uistate.UiState
import com.compose.presentation.viewmodel.CharacterDetailsViewModel

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    characterId: String,
    toolbarTitle: String,
    onBackButtonPressed: () -> Unit
) {
    LaunchedEffect(characterId) {
        viewModel.getCharacterDetails(characterId)
    }
    val characterState by viewModel.characterDetailsState.collectAsStateWithLifecycle()
    CharacterDetailsItems(characterState, toolbarTitle, onBackButtonPressed = onBackButtonPressed)
}

@Composable
fun CharacterDetailsItems(
    characterState: UiState,
    toolbarTitle: String,
    modifier: Modifier = Modifier,
    onBackButtonPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.paddingSmall),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CustomAppBar(toolbarTitle, onBackButtonPressed = onBackButtonPressed)
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
            DisplayCharacterDetailsData(modifier, it as Character)
        }
    }
}

@Composable
fun DisplayCharacterDetailsData(
    modifier: Modifier = Modifier,
    character: Character,
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
        Text(
            text = character.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
        )
        CharacterStatus(modifier, character)
    }
}

@Composable
fun CharacterStatus(modifier: Modifier, character: Character) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingLarge
        )
    ) {
        Text(
            text = stringResource(R.string.status),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.status.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    CharacterSpecies(modifier, character)
}

@Composable
fun CharacterSpecies(modifier: Modifier, character: Character) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.species),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.species.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    CharacterGender(modifier, character)
}

@Composable
fun CharacterGender(modifier: Modifier, character: Character) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.gender),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.gender.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    CharacterOrigin(modifier, character)
}

@Composable
fun CharacterOrigin(modifier: Modifier, character: Character) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.origin),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingMedium
        )
    ) {
        Text(
            text = stringResource(R.string.origin_name),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.origin.name.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.origin_dimension),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.origin.dimension.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    CharacterLocation(modifier, character)
}

@Composable
fun CharacterLocation(modifier: Modifier, character: Character) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.location),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingMedium
        )
    ) {
        Text(
            text = stringResource(R.string.location_name),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.location.name.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.location_dimension),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = character.location.dimension.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
            color = Color.Black
        )
    }
    DisplayEpisodes(modifier, character)
}

@Composable
fun DisplayEpisodes(modifier: Modifier, character: Character) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.episodes),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingMedium))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = MaterialTheme.dimens.paddingLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingSmall)
    ) {
        character.episodes?.size?.let {
            items(
                it,
                key = { item -> character.episodes?.get(item)?.id!! }
            ) { index ->
                EpisodesItem(modifier, episode = character.episodes?.get(index)!!)
            }
        }
    }
}

@Composable
fun EpisodesItem(modifier: Modifier, episode: Episode) {
    Card(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.paddingSmall)
            .width(MaterialTheme.dimens.cardWidth)
            .height(MaterialTheme.dimens.cardWidth)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(MaterialTheme.dimens.paddingLarge)
        ) {
            Text(text = episode.name.toString(), fontSize = MaterialTheme.typography.labelLarge.fontSize)
        }
    }
}
