package com.mindyug.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun RequestPermissionScreen(
    onClick: () -> Unit,
) {
    MindYugTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome to MindYug!", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier
                        .size(60.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We need usage access permission of your\nphone to show the statistics in the app.\n" +
                            "We do not store your app usage\nbehaviour in cloud.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.5f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,

                    )
                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(onClick = onClick) {
                    Text(text = "Open Settings")
                }

            }
        }
    }
}