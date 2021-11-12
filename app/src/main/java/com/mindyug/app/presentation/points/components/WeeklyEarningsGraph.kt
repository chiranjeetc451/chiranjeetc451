package com.mindyug.app.presentation.points.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun WeeklyEarningsGraph(

) {
    Row{
        var progress by remember { mutableStateOf(0.1f) }


        LinearProgressIndicator(
            backgroundColor = Color.White,
            progress = progress,
            color = Color.Black
        )
    }
}