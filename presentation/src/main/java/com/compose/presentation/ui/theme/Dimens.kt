package com.compose.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val padding4dp: Dp,
    val padding8dp: Dp,
    val padding12dp: Dp,
    val padding16dp: Dp,
    val padding20dp: Dp,
    val margin4dp: Dp,
    val margin8dp: Dp,
    val margin12dp: Dp,
    val cardWidth: Dp
)

val RMDimens = Dimens(
    padding4dp = 4.dp,
    padding8dp = 8.dp,
    padding12dp = 12.dp,
    padding16dp = 16.dp,
    padding20dp = 20.dp,
    margin4dp = 4.dp,
    margin8dp = 8.dp,
    margin12dp = 12.dp,
    cardWidth = 100.dp
)

val LocalDimens = compositionLocalOf<Dimens> { error("No dimens provided") }
val MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalDimens.current
