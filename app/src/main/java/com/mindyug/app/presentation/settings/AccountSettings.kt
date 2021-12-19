package com.mindyug.app.presentation.settings

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun AccountSettings(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),

    ) {
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
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "back",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(98.dp))
                    Text(
//                        modifier = Modifier.fillMaxWidth(),
                        text = "Account Settings",
                        color = Color.White,
//                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = Color(0xFF0D3F56)
        ) {
            val context = LocalContext.current
            val sharedPref =
                context.getSharedPreferences("userLoginState", Context.MODE_PRIVATE)
            val uid = sharedPref.getString("uid", null)
            val loadingState = viewModel.accountSettingsState.value
            val phoneNumber = viewModel.phoneNumber.value
            val userName = sharedPref.getString("name", null)

            val name = viewModel.name.value
            val save = viewModel.btnSave

            val address = viewModel.addressState.value


            LaunchedEffect(Unit) {
                viewModel.getUser(uid!!)
            }


            if (loadingState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {


                    LaunchedEffect(Unit) {
                        viewModel.getProfilePictureUri(uid!!)
                    }

                    val emptyImage =
                        Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}")


                    var text by remember { mutableStateOf("") }
                    val imageUri = viewModel.profilePictureUri.value.uri


                    val launcher = rememberLauncherForActivityResult(
                        contract =
                        ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        if (uri != null) {
                            viewModel.changeToNewProfilePic(uri)
                        } else {
                            viewModel.changeToNewProfilePic(emptyImage)
                            viewModel.getProfilePictureUri(uid!!)
                        }
                    }

                    val painter = rememberImagePainter(
                        data = imageUri, builder = {
                            transformations(
                                CircleCropTransformation()
                            )
                            placeholder(R.drawable.ic_profile_pic)
                            error(R.drawable.ic_profile_pic)
                        })

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable(enabled = true, onClick = {
                                    launcher.launch("image/*")
                                }),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(CircleShape)
                                    .shadow(8.dp),
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
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Edit profile picture",
                            color = Color.White,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Registered Phone Number:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = phoneNumber.text,
                        onValueChange = { viewModel.editName(it, phoneNumber.text) },
                        enabled = false,
                        placeholder = {
                            Text(
                                text = "Enter Number",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Name:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = name.text,
                        onValueChange = {
                            viewModel.editName(it, userName!!)
                        },
                        placeholder = {
                            Text(
                                text = "Enter Name",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    if (name.isError) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please enter a valid name.",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Start
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GradientButton(
                            onClick = {
                                viewModel.uploadProfilePicFromUid(uid!!)
                                sharedPref.edit().putString("name", name.text).apply()
                                viewModel.updateUserData(
                                    uid!!,
                                    name.text!!,
                                    phoneNumber.text!!,
                                    address.houseNo!!,
                                    address.addressLineOne,
                                    address.addressLineTwo,
                                    address.pinCode,
                                    address.landmark,
                                    address.city,
                                    address.state,
                                    address.country,
                                    navHostController,

                                )
                            },
                            enabled = save.value.isEnabled
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Save", color = Color.White)
                            Spacer(modifier = Modifier.width(16.dp))

                        }
                    }

                }
            }
        }
    }
}