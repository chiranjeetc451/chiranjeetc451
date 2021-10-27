package com.mindyug.app.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography

@Composable
fun EnterNameScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController,
    verifiedNumber: String,
) {
    val name = viewModel.name.value
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
                    .padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "My name is",
                    style = Typography.h4
                )


                Column(modifier = Modifier.padding(20.dp)) {
                    TextField(
                        value = name.text,
                        onValueChange = { viewModel.onEvent(UserDataEvent.EnteredName(it)) },
                        placeholder = {
                            Text(
                                text = name.hint,
                                Modifier.alpha(0.5f)
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
                    if (name.isError) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please enter a valid name.",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Start
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(onClick = {

                    if (!name.isError && name.text.isNotEmpty()) {
                        navHostController.navigate(Screen.EnterUsernameScreen.withArgs(verifiedNumber, name.text))
                    } else {
                        if (name.text.isEmpty() || name.text == "") {
                            Toast.makeText(
                                context,
                                "Field cannot be left blank.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter a valid name.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }) {
                    Text(text = "Next")
                }

//                Log.d("tag", verifiedNumber)
            }
        }

    }
}

