package ru.mvrlrd.main.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun CompanionTheme(
    darkTheme: Boolean,
    typography: AppTypography = AppTypography(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) darkColorPalette() else lightColorPalette()
    CompositionLocalProvider(
        LocalColor provides colors,
        LocalTypography provides typography,
    ) {
        androidx.compose.material3.MaterialTheme(
            colorScheme = colors.materialColors,
            typography = typography.materialTypography,
        ) {
            content()
        }
    }
}

internal val LocalColor = staticCompositionLocalOf { lightColorPalette() }
internal val LocalTypography = staticCompositionLocalOf { AppTypography() }
