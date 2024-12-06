package com.compose.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val paddingExtraSmall: Dp,
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
    val paddingExtraLarge: Dp,
    val margin4dp: Dp,
    val margin8dp: Dp,
    val margin12dp: Dp,
    val cardWidth: Dp
)

val RMDimens = Dimens(
    paddingExtraSmall = 4.dp,
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingLarge = 16.dp,
    paddingExtraLarge = 20.dp,
    margin4dp = 4.dp,
    margin8dp = 8.dp,
    margin12dp = 12.dp,
    cardWidth = 100.dp
)

val LocalDimens = compositionLocalOf<Dimens> { error("No dimens provided") }
val MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalDimens.current
