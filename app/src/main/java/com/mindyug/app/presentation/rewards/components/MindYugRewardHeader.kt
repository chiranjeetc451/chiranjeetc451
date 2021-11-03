package com.mindyug.app.presentation.rewards.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MinYugRewardHeader(label: String) {
    Row(modifier = Modifier.padding(start = 28.dp, end = 16.dp)) {
        Text(
            modifier = Modifier.weight(0.5f),
            text = label,
            textAlign = TextAlign.Start
        )
        Row(
            modifier = Modifier
                .weight(0.5f),
            horizontalArrangement = Arrangement.End

        ) {
            Row(modifier = Modifier.clickable {

            }) {
                Text(
                    text = "View all",
                    textAlign = TextAlign.End,

                    )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }


    }
    Spacer(modifier = Modifier.height(4.dp))

}