package com.githukudenis.smartduka.designsystem.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = DarkPrimary,
        secondary = DarkSecondary,
        background = DarkBackground,
        surface = DarkSurface,
        error = DarkError,
        onPrimary = DarkOnPrimary,
        onSecondary = DarkOnSecondary,
        onBackground = DarkOnBackground,
        onSurface = DarkOnSurface,
        onError = DarkOnError
    )

private val LightColorScheme =
    lightColorScheme(
        primary = LightPrimary,
        secondary = LightSecondary,
        background = LightBackground,
        surface = LightSurface,
        error = LightError,
        onPrimary = LightOnPrimary,
        onSecondary = LightOnSecondary,
        onBackground = LightOnBackground,
        onSurface = LightOnSurface,
        onError = LightOnError
    )

@Composable
fun SmartDukaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    CompositionLocalProvider(
        LocalGradients provides Gradients,
        LocalShapes provides Shapes,
        LocalDimens provides Dimens
    ) {
        MaterialTheme(colorScheme = colorScheme, typography = smartdukaTypography, content = content)
    }
}
