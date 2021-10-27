package com.mindyug.app.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography

@Composable
fun EnterUsernameScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController,
    number: String,
    name: String,


    ) {
    val username = viewModel.username.value
    val context = LocalContext.current

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
        }){
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Username",
                    style = Typography.h4
                )
                Column(modifier = Modifier.padding(20.dp)) {
                    TextField(
                        value = username.text,
                        onValueChange = { viewModel.onEvent(UserDataEvent.EnteredUsername(it)) },
                        placeholder = {
                            Text(
                                text = username.hint,
                                Modifier.alpha(0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AlternateEmail,
                                contentDescription = "notifications",
                                tint = Color.White
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    if (username.isError) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please Enter a valid username.",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Start
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(onClick = {
                    navHostController.navigate(
                        Screen.UploadPhotoScreen.withArgs(
                            number,
                            name,
                            username.text
                        )
                    )
                }) {
                    Text(text = "Next")
                }


            }
        }


    }

}

