package com.mindyug.app.presentation.points.components

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindyug.app.common.components.GradientButton

@Composable
fun CollectSection(
    points: Long,
    onClick: () -> Unit,

    ) {
    Surface(
//        modifier = Modifier.width(110.dp),
        shape = RoundedCornerShape(percent = 50)
    ) {
        Row() {

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .width(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Text(
                    text = points.toString(),
                    style = MaterialTheme.typography.body2
                )

            }
            Column(

//                    .height(30.dp)
            ) {
                GradientButton(onClick = onClick,brush1 =gradientBrush) {
                    Text(text = "Collect", color = Color.White, )

                }
            }

        }


    }

}

val gradientBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xff1C6586),
        Color(0xff6F67C6),
    )
)