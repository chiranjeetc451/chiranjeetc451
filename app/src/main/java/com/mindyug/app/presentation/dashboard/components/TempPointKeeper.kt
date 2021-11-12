package com.mindyug.app.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TempPointKeeper(
    points: Long,
) {
    Surface(
        modifier = Modifier.width(110.dp),
        shape = RoundedCornerShape(percent = 50)
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .weight(0.5f)
                    .background(
                        when {
                            points >= 700 -> {
                                MaterialTheme.colors.secondary
                            }
                            points >= 200 -> {
                                Color(0xFFBDC02C)
                            }
                            else -> {
                                Color.Red
                            }
                        }
                    )
                    .height(30.dp)
            ) {
                Text(text = "            ")
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .height(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Text(
                    text = points.toString(),
                    style = MaterialTheme.typography.body2
                )

            }

        }


    }

}

@Preview
@Composable
fun TempPointKeeperPreview() {
    TempPointKeeper(
        9000,
    )
}