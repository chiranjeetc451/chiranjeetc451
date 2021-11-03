package com.mindyug.app.presentation.rewards.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mindyug.app.R

@Composable
fun MindYugRewardCard(
    modifier: Modifier,
    label: String,
    brush: Brush,
    id: Int

) {
    Column(
        modifier = modifier
            .shadow(8.dp)
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                brush
            )

            .clickable { }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(85.dp),
            painter = painterResource(id = id),
            contentDescription = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.h6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
        }
    }
}