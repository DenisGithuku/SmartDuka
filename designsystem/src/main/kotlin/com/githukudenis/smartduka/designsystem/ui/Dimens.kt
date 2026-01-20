package com.githukudenis.smartduka.designsystem.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimens {
    // Border width
    val thinWidth: Dp = 1.dp
    val regularWidth: Dp = 2.dp
    val thickWidth: Dp = 4.dp

    // Spacing
    val extraSmallPadding: Dp = 4.dp
    val smallPadding: Dp = 8.dp
    val mediumPadding: Dp = 16.dp
    val largePadding: Dp = 24.dp
    val extraLargePadding: Dp = 32.dp

    // Corner Radii
    val extraSmallCornerRadius: Dp = 4.dp
    val smallCornerRadius: Dp = 8.dp
    val mediumCornerRadius: Dp = 12.dp
    val largeCornerRadius: Dp = 16.dp

    // Elevation
    val extraSmallElevation: Dp = 2.dp
    val smallElevation: Dp = 4.dp
    val mediumElevation: Dp = 8.dp

    // Icon Sizes
    val extraSmallIcon: Dp = 16.dp
    val smallIcon: Dp = 24.dp
    val mediumIcon: Dp = 32.dp
    val largeIcon: Dp = 48.dp
}

val LocalDimens = staticCompositionLocalOf { Dimens }
