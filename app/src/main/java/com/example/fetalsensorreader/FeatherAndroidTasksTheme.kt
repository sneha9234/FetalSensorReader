package com.example.fetalsensorreader

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FeatherAndroidTasksTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme(
            primary = Color(0xFF6200EE),
            onPrimary = Color.White,
            primaryContainer = Color.Gray,
            onPrimaryContainer = Color.Black,
            inversePrimary = Color.Red,
            secondary = Color(0xFF03DAC6),
            onSecondary = Color.Black,
            secondaryContainer = Color.Yellow,
            onSecondaryContainer = Color.Black,
            tertiary = Color.Green,
            onTertiary = Color.Black,
            tertiaryContainer = Color.Blue,
            onTertiaryContainer = Color.Black,
            background = Color.White,
            onBackground = Color.Black,
            surface = Color.Gray,
            onSurface = Color.Black,
            surfaceVariant = Color.DarkGray,
            onSurfaceVariant = Color.White,
            surfaceTint = Color.LightGray,
            inverseSurface = Color.Black,
            inverseOnSurface = Color.White,
            error = Color.Red,
            onError = Color.White,
            errorContainer = Color.Magenta,
            onErrorContainer = Color.Yellow,
            outline = Color.Cyan,
            outlineVariant = Color.Magenta,
            scrim = Color.Black.copy(alpha = 0.5f)
        ),
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}