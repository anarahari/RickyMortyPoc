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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.compose.domain.mapper.Character
import com.compose.domain.mapper.Episode
import com.compose.domain.mapper.Location
import com.compose.domain.mapper.Origin
import com.compose.presentation.R
import com.compose.presentation.ui.theme.dimens
import com.compose.presentation.uistate.UiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    characterDetails: StateFlow<UiState>,
    characterId: String,
    toolbarTitle: String,
    onBackButtonPressed: () -> Unit
) {
    val characterState by characterDetails.collectAsStateWithLifecycle()
    CharacterDetailsItems(
        modifier,
        characterState,
        toolbarTitle,
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
private fun CharacterDetailsItems(
    modifier: Modifier,
    characterState: UiState,
    toolbarTitle: String,
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
                text = characterState.error,
                color = MaterialTheme.colorScheme.error,
            )
        }

        characterState.data?.let {
            DisplayCharacterDetailsData(modifier, it as Character)
        }
    }
}

@Composable
private fun DisplayCharacterDetailsData(
    modifier: Modifier = Modifier,
    character: Character,
) {
    val scrollState = rememberScrollState()
    Column(modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
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
        Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
        Text(
            text = character.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
        )
        CharacterStatus(modifier, character.status.toString())
        CharacterSpecies(modifier, character.species.toString())
        CharacterGender(modifier, character.gender.toString())
        CharacterOrigin(modifier, character.origin)
        CharacterLocation(modifier, character.location)
        character.episodes?.let { DisplayEpisodes(modifier, it) }
    }
}

@Composable
private fun CharacterStatus(modifier: Modifier = Modifier, status: String) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingLarge
        )
    ) {
        Text(
            text = stringResource(R.string.status),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
        )
    }
}

@Composable
private fun CharacterSpecies(modifier: Modifier, species: String) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.species),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = species.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingSmall)
        )
    }
}

@Composable
private fun CharacterGender(modifier: Modifier, gender: String) {
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingSmall
        )
    ) {
        Text(
            text = stringResource(R.string.gender),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = gender.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingSmall)
        )
    }
}

@Composable
private fun CharacterOrigin(modifier: Modifier, origin: Origin) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.origin),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingMedium
        )
    ) {
        Text(
            text = stringResource(R.string.origin_name),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = origin.name.toString().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingSmall)
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
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = origin.dimension.toString().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(start = MaterialTheme.dimens.paddingSmall),
        )
    }
}

@Composable
private fun CharacterLocation(modifier: Modifier, location: Location) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.location),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Row(
        modifier = modifier.padding(
            start = MaterialTheme.dimens.paddingExtraLarge,
            top = MaterialTheme.dimens.paddingMedium
        )
    ) {
        Text(
            text = stringResource(R.string.location_name),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = location.name.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingSmall),
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
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = location.dimension.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.paddingSmall)
        )
    }
}

@Composable
private fun DisplayEpisodes(modifier: Modifier, episodes: List<Episode>) {
    Spacer(modifier = modifier.padding(top = MaterialTheme.dimens.paddingExtraLarge))
    Text(
        text = stringResource(R.string.episodes),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = MaterialTheme.dimens.paddingExtraLarge)
    )
    Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.paddingMedium))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = MaterialTheme.dimens.paddingLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingSmall)
    ) {
        items(
            episodes.size,
            key = { item -> episodes[item].id.toString() }
        ) { index ->
            EpisodesItem(modifier, episode = episodes[index])
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
            Text(
                text = episode.name.toString(),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        }
    }
}
