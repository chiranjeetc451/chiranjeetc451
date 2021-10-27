package com.mindyug.app.presentation.login

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography


@ExperimentalCoilApi
@Composable
fun UploadPhotoScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController,
    number: String,
    name: String,
    username: String,
) {

    var value by remember { mutableStateOf("") }

    var imageUri by remember {
        mutableStateOf<Uri?>(Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}"))
    }

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val btnNext = viewModel.btnNext.value


    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    MindYugTheme {
        Scaffold(topBar = {
            IconButton(
                onClick = { navHostController.navigateUp() },
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIos,
                    contentDescription = "back",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp),
                    text = "Upload Photo",
                    style = Typography.h4
                )
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .padding(20.dp)

                        .clickable(enabled = true, onClick = {
                            launcher.launch("image/*")
                        }),
                    contentAlignment = Alignment.BottomEnd
                ) {

                    val painter = rememberImagePainter(
                        data = imageUri, builder = {
                            placeholder(R.drawable.ic_profile_pic)
                            error(R.drawable.ic_profile_pic)
                            transformations(
                                CircleCropTransformation()
                            )

                        })

                    val painterState = painter.state
                    if (painterState is ImagePainter.State.Error) {
                        imageUri =
                            Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}")
                    }

                    Image(
                        modifier = Modifier
                            .height(300.dp)
                            .width(300.dp),
                        contentScale = ContentScale.Fit,
                        painter = painter,
                        contentDescription = ""
                    )
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                }
                Log.d("tag", FirebaseAuth.getInstance().currentUser?.uid!!)

                Spacer(modifier = Modifier.height(40.dp))

                GradientButton(
                    onClick = {
//                    navHostController.navigate(Screen.HomeScreen.route)
                        imageUri?.let { it1 -> viewModel.uploadProfilePic(it1) }
                        viewModel.addUser(
                            name = name,
                            username = username,
                            number = number,
                            context = context,
                            navController = navHostController
                        )
                    },
                    enabled = btnNext.isEnabled
                ) {
                    Text(text = "Get started")
                }
            }
        }

    }
}




