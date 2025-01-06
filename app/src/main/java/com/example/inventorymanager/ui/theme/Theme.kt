package com.example.inventorymanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BlueNavy,
    secondary = BlueSky,
    background = GrayLead,
    surface = GrayLight,
    onPrimary = White,
    onSecondary = BlueNavy,
    onBackground = White,
    onSurface = BlueNavy
)

private val LightColorScheme = lightColorScheme(
    primary = BlueNavy,
    secondary = BlueSky,
    background = White,
    surface = GrayLight,
    onPrimary = White,
    onSecondary = BlueNavy,
    onBackground = BlueNavy,
    onSurface = BlueNavy
)

@Composable
fun InventoryManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}