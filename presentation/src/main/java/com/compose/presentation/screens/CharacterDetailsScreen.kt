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
import com.compose.domain.mapper.CharacterModel
import com.compose.domain.mapper.EpisodeModel
import com.compose.presentation.R
import com.compose.presentation.ui.theme.dimens
import com.compose.presentation.uistate.UiState
import com.compose.presentation.viewmodel.CharacterViewModel

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterViewModel,
    characterId: String,
    toolbarTitle: String,
    onBackButtonPressed: () -> Unit
) {
    LaunchedEffect(characterId) {
        viewModel.getCharacterDetails(characterId)
    }
    val characterState by viewModel.characterDetailsState.collectAsStateWithLifecycle()
    CharacterScreenLoadData(characterState, toolbarTitle, onBackButtonPressed = onBackButtonPressed)
}

@Composable
fun CharacterScreenLoadData(
    characterState: UiState,
    toolbarTitle: String,
    modifier: Modifier = Modifier,
    onBackButtonPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.padding8dp),
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
            DisplayData(it as CharacterModel)
        }
    }
}

@Composable
fun DisplayData(
    characterModel: CharacterModel,
    modifier: Modifier = Modifier
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = characterModel.image,
                contentDescription = characterModel.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier.padding(top = MaterialTheme.dimens.padding20dp))
        Text(
            text = characterModel.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding20dp)
        )
        CharacterStatus(characterModel, modifier)
    }
}

@Composable
fun CharacterStatus(characterModel: CharacterModel, modifier: Modifier) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding16dp
        )
    ) {
        Text(
            text = stringResource(R.string.status),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.status.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    CharacterSpecies(characterModel, modifier)
}

@Composable
fun CharacterSpecies(characterModel: CharacterModel, modifier: Modifier) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding8dp
        )
    ) {
        Text(
            text = stringResource(R.string.species),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.species.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    CharacterGender(characterModel, modifier)
}

@Composable
fun CharacterGender(characterModel: CharacterModel, modifier: Modifier) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding8dp
        )
    ) {
        Text(
            text = stringResource(R.string.gender),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.gender.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    CharacterOrigin(characterModel, modifier)
}

@Composable
fun CharacterOrigin(characterModel: CharacterModel, modifier: Modifier) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.padding20dp))
    Text(
        text = stringResource(R.string.origin),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.padding20dp)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding12dp
        )
    ) {
        Text(
            text = stringResource(R.string.origin_name),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.origin.name.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding8dp
        )
    ) {
        Text(
            text = stringResource(R.string.origin_dimension),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.origin.dimension.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    CharacterLocation(characterModel, modifier)
}

@Composable
fun CharacterLocation(characterModel: CharacterModel, modifier: Modifier) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.padding20dp))
    Text(
        text = stringResource(R.string.location),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.padding20dp)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding12dp
        )
    ) {
        Text(
            text = stringResource(R.string.location_name),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.location.name.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.padding20dp,
            top = MaterialTheme.dimens.padding8dp
        )
    ) {
        Text(
            text = stringResource(R.string.location_dimension),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = characterModel.location.dimension.orEmpty().uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(start = MaterialTheme.dimens.padding8dp),
            color = Color.Black
        )
    }
    DisplayEpisodes(characterModel, modifier)
}

@Composable
fun DisplayEpisodes(characterModel: CharacterModel, modifier: Modifier) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.padding20dp))
    Text(
        text = stringResource(R.string.episodes),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = modifier.padding(start = MaterialTheme.dimens.padding20dp)
    )
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.padding12dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = MaterialTheme.dimens.padding16dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.padding8dp)
    ) {
        characterModel.episodes?.size?.let {
            items(
                it,
                key = { item -> characterModel.episodes?.get(item)?.id!! }
            ) { index ->
                EpisodesItem(episode = characterModel.episodes?.get(index)!!, modifier)
            }
        }
    }
}

@Composable
fun EpisodesItem(episode: EpisodeModel, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.padding8dp)
            .width(MaterialTheme.dimens.cardWidth)
            .height(MaterialTheme.dimens.cardWidth)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(MaterialTheme.dimens.padding16dp)
        ) {
            Text(text = episode.name, fontSize = MaterialTheme.typography.labelLarge.fontSize)
        }
    }
}
