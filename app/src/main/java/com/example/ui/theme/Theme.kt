package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SafetyOrangeLight,
    onPrimary = Color.Black,
    primaryContainer = SafetyOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = SafetyYellow,
    onSecondary = Color.Black,
    tertiary = SafetyBlue,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    error = SafetyRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = SafetyOrange,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDD5),
    onPrimaryContainer = Color(0xFF7C2D12),
    secondary = SafetyAmber,
    onSecondary = Color.White,
    tertiary = SafetyBlue,
    background = IndustrialBgLight,
    surface = IndustrialCardLight,
    onBackground = IndustrialTextPrimary,
    onSurface = IndustrialTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = IndustrialTextSecondary,
    error = SafetyRed,
    onError = Color.White
)

@Composable
fun SafetyIndustrialTheme(
    darkTheme: Boolean = true, // Industrial dark UI default
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SafetyIndustrialTheme(darkTheme = darkTheme, content = content)
}
