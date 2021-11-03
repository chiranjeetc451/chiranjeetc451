package com.mindyug.app.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mindyug.app.R
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    MindYugTheme {
        Scaffold(
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navHostController.navigateUp() },
                    ) {
                        Icon(
                            modifier = Modifier.weight(0.2f),
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "back",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.6f),
                        text = "Profile",
                        color = Color.White,
                        textAlign = TextAlign.Center,

                        )
                    IconButton(
                        onClick = { navHostController.navigate(Screen.SettingsScreen.route) },
                    ) {
                        Icon(
                            modifier = Modifier.weight(0.2f),

                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "back",
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {


            }
        }
    }

}