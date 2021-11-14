package com.mindyug.app.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.api.ResourceDescriptor
import com.mindyug.app.R
import com.mindyug.app.common.components.PointsList
import com.mindyug.app.common.components.getDateAsStringFromDate
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.presentation.dashboard.DashboardViewModel
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import java.util.*

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
                        onClick = { navHostController.navigate(Screen.HomeScreen.route) },
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
            val context = LocalContext.current
            val sharedPref = context.getSharedPreferences("userLoginState", Context.MODE_PRIVATE)
            val uid = sharedPref.getString("uid", null)
            val name = sharedPref.getString("name", null)

            LaunchedEffect(Unit) {
                viewModel.getProfilePictureUri(uid!!)
            }

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
                    text = name!!,
                    color = Color.White,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(8.dp))

                val ab = mutableListOf(PointItem(getDateAsStringFromDate(Date()),"ss","sss","sss",-90000), PointItem(getDateAsStringFromDate(Date()),"ss","sss","sss",97000), PointItem(getDateAsStringFromDate(Date()),"ss","sss","sss",80000))

                PointsList(ab, true)


            }
        }
    }

}