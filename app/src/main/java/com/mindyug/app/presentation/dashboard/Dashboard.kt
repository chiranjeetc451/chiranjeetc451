package com.mindyug.app.presentation.dashboard

import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mindyug.app.presentation.dashboard.components.AnimatedCircle

@Composable
fun Dashboard() {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(25.dp))
        val abc = listOf(0.5f, 0.5f)
        val bdc = listOf(Color.DarkGray, Color.Black)
        AnimatedCircle(
            proportions = abc, colors = bdc,
            modifier = Modifier
                .height(300.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))


//
//        Toast.makeText(
//            context,
//            "Verification ooop ${highScore.toString()}",
//            Toast.LENGTH_SHORT
//        ).show()

    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            IconButton(
                onClick = { /* TODO: Open menu */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /* TODO: Open notifications */ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "notifications"
                )
            }
            IconButton(
                onClick = { /* TODO: Open account? */ }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "account"
                )
            }
        },
        elevation = 0.dp
    )
}