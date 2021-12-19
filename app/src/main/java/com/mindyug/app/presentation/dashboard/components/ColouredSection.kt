package com.mindyug.app.presentation.dashboard.components

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindyug.app.domain.model.AppStat

@Composable
fun ColouredSection(
    context: Context,
    list: MutableList<AppStat>,
    colors: List<Color> = graphColors,
) {

    ColorRow(
        color = colors[0],
        name = getApplicationNameFromPackageName(context, list[0].packageName)
    )
    ColorRow(
        color = colors[1],
        name = getApplicationNameFromPackageName(context, list[1].packageName)
    )
    ColorRow(
        color = colors[2],
        name = getApplicationNameFromPackageName(context, list[2].packageName)
    )
    ColorRow(
        color = colors[3],
        name = getApplicationNameFromPackageName(context, list[3].packageName)
    )
    ColorRow(
        color = colors[4],
        name = getApplicationNameFromPackageName(context, list[4].packageName)
    )
    ColorRow(color = colors[5], name = "Others")


}

fun getApplicationNameFromPackageName(context: Context, packageName: String): String {
    val ai: ApplicationInfo =
        context.packageManager.getApplicationInfo(packageName, 0)

    return context.packageManager.getApplicationLabel(ai) as String
}

@Composable
fun ColorRow(
    color: Color,
    name: String
) {
    Row(
        modifier = Modifier.padding(2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = color)
                .size(10.dp)
        ) {
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name)
    }
}