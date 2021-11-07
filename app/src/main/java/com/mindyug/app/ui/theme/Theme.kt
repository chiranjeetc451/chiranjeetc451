package com.mindyug.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColors(
    primary = DarkBlue,
    secondary = LightGreen,
    background = DarkBlue

)

@Composable
fun MindYugTheme(content: @Composable() () -> Unit) {
    val colors = LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}