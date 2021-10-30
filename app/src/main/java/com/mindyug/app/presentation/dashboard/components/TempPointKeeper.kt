package com.mindyug.app.presentation.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TempPointKeeper() {
    Surface(
        shape = RoundedCornerShape(percent = 50)
    ) {
        Row() {
            Box(modifier= Modifier.weight(0.5f)) {
//                Surface(
//                    color = Color.Black,
//                    shape = RoundedCornerShape(percent = 50)
//                ) {
                    Text(text = "       tt  ")

//                }
            }
            Box(modifier= Modifier.weight(0.5f)) {
                Text(text = "hello ")
            }
        }


    }

}

@Preview
@Composable
fun TempPointKeeperPreview() {
    TempPointKeeper()
}