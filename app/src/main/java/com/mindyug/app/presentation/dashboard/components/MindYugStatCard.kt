package com.mindyug.app.presentation.dashboard.components

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mindyug.app.R

@Composable
fun MindYugStatCard(
    context: Context,
    packageName: String,
    progress: Float,

) {
    val ai: ApplicationInfo =
        context.packageManager.getApplicationInfo(packageName, 0)

    val icon: Drawable = context.packageManager.getApplicationIcon(ai)

    val painter = rememberImagePainter(
        data = icon, builder = {
            transformations(
                CircleCropTransformation()
            )
            placeholder(R.drawable.ic_profile_pic)
            error(R.drawable.ic_profile_pic)
        })

    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFF094561),
        modifier = Modifier
            .padding(8.dp),
        elevation = 16.dp

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(150.dp)
                .width(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painter,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .height(6.dp),
                backgroundColor = Color.White,
                progress = progress,
                color = Color.Green
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "2 Hr. 30 Min.",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption
            )

        }


    }
}