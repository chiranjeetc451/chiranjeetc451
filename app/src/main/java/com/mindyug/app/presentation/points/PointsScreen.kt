package com.mindyug.app.presentation.points

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun PointsScreen(){
    MindYugTheme {
        Scaffold() {
            Column(
                modifier= Modifier.fillMaxSize().background(Color(0xFF0D3F56)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(text = "jlll", color= Color.White)
                
            }
            
        }
    }
}