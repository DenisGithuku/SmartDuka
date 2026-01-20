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
import androidx.compose.ui.graphics.Brush

object Gradients {
    val primaryGradient = Brush.horizontalGradient(colors = listOf(LightPrimary, LightPrimaryVariant))

    val secondaryGradient =
        Brush.verticalGradient(colors = listOf(LightSecondary, LightSecondaryVariant))

    val tertiaryGradient = Brush.horizontalGradient(colors = listOf(LightBackground, LightSurface))

    val darkPrimaryGradient =
        Brush.horizontalGradient(colors = listOf(DarkPrimary, DarkPrimaryVariant))

    val darkSecondaryGradient =
        Brush.verticalGradient(colors = listOf(DarkSecondary, DarkSecondaryVariant))

    val darkTertiaryGradient = Brush.horizontalGradient(colors = listOf(DarkBackground, DarkSurface))
}

val LocalGradients = staticCompositionLocalOf { Gradients }
