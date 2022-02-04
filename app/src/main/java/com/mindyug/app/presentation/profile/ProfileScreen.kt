package com.mindyug.app.presentation.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mindyug.app.R
import com.mindyug.app.presentation.profile.components.StatsList
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),


    ) {
    MindYugTheme {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navHostController.navigateUp() },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "back",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        text = "Profile",
                        color = Color.White,
                        textAlign = TextAlign.Center,

                        )
                    IconButton(
                        onClick = { navHostController.navigate(Screen.SettingsScreen.route) },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                }
            },
            backgroundColor = Color(0xFF0D3F56)
        ) {

            val imageUri = viewModel.profilePictureUri.value.uri


            val painter = rememberImagePainter(
                data = imageUri, builder = {
                    transformations(
                        CircleCropTransformation()
                    )
                    placeholder(R.drawable.ic_profile_pic)
                    error(R.drawable.ic_profile_pic)
                })

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .shadow(8.dp),
                    contentScale = ContentScale.Fit,
                    painter = painter,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = viewModel.name.value,
                    color = Color.White,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(8.dp))


                StatsList(
                    list = viewModel.listState.value.list,
                    isLoading = viewModel.listState.value.isLoading
                )

            }
        }
    }

}