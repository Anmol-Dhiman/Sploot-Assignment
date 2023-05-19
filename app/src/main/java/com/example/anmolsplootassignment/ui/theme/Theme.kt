package com.example.anmolsplootassignment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,

    background = Blur,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Button,
    onSecondary = Text,
    onSurface = Heading,


    )

@Composable
fun AnmolSplootAssignmentTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}