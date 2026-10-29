package com.alifwanoon.v72.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF6D5BFF),
    secondary = Color(0xFF00F5C8),
    background = Color(0xFF0A0B10),
    surface = Color(0xFF13151F)
)

@Composable
fun AlifWaNoonTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkScheme, content = content)
}
