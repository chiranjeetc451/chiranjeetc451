package com.mindyug.app.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindyug.app.R

@Composable
fun PointKeeper(
    color: Color = Color(0xFF002333),
    score: String,
    isLoading: Boolean
) {
    Surface(
        color = Color(0xFF2CE07F),
        shape = RoundedCornerShape(percent = 50)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                )
            } else {
                Text(
                    text = score,
                    color = color
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Divider(
                color = color,
                modifier = Modifier
                    .width(2.dp)
                    .height(24.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_shop),
                contentDescription = "shopping bag",
                tint = color
            )
            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

@Preview
@Composable
fun PointKeeperPreview() {
    PointKeeper(Color.Black, "5000", true)
}