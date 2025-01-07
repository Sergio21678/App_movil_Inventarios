// Theme.kt
package com.example.inventorymanager.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BlueNavy,
    onPrimary = White,
    secondary = GrayLead,
    onSecondary = White,
    background = GrayLight,
    onBackground = BlueNavy,
    surface = GrayLead,
    onSurface = White,
    error = Color.Red,
    onError = White
)

private val LightColorScheme = lightColorScheme(
    primary = BlueSky,
    onPrimary = BlueNavy,
    secondary = GrayLight,
    onSecondary = BlueNavy,
    background = White,
    onBackground = BlueNavy,
    surface = White,
    onSurface = BlueNavy,
    error = Color.Red,
    onError = White
)

@Composable
fun InventoryManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}