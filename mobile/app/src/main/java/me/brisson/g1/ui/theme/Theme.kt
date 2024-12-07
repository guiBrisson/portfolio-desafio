package me.brisson.g1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Red500,
    secondary = Red500,
    tertiary = Red500,
    background = Color.White,
    onBackground = Color.Black,
    onPrimary = Red200,
    onSecondary = Red200,
    onTertiary = Red200,
    primaryContainer = Red500,
    onPrimaryContainer = Red200,
)

@Composable
fun G1Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}