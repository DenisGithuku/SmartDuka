/*
* Copyright 2026 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
