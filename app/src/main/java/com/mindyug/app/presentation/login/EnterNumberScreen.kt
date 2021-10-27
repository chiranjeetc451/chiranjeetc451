package com.mindyug.app.presentation.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.firebase.firestore.auth.User
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography
import java.util.regex.Pattern

@Composable
fun EnterNumberScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onClick: (mobileNum: String) -> Unit,

    ) {
    val phoneNumber = viewModel.phoneNumber.value
    val context = LocalContext.current

    MindYugTheme {
        Scaffold(
            topBar = {
                IconButton(
                    onClick = { navHostController.navigateUp() },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIos,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            },
        ) {


            PhoneNumberConsent {
                viewModel.onEvent(UserDataEvent.EnteredPhoneNumber(it.drop(3)))
            }


            val annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.learn_otp))
                pushStringAnnotation(
                    tag = "learn_more",// provide tag which will then be provided when you click the text
                    annotation = "learn_more"
                )
                withStyle(style = SpanStyle(color = Color(0xFFB5B4ED))) {
                    append(stringResource(R.string.learn_otp_two))
                }
                pop()
            }
            Column(
                modifier = Modifier
                    .padding(8.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Column(modifier = Modifier.padding()) {
                    Text(
//                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp),
                        text = stringResource(R.string.my_number_is),
                        style = Typography.h4
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = phoneNumber.text,
                        onValueChange = {
                            viewModel.onEvent(UserDataEvent.EnteredPhoneNumber(it))
                        },
                        placeholder = {
                            Text(
                                text = phoneNumber.hint,
                                Modifier.alpha(0.5f)
                            )
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_india),
                                contentDescription = "Indian Flag",
                                contentScale = ContentScale.Inside
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardActions = KeyboardActions { phoneNumber.isError },

                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                    if (phoneNumber.isError) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please Enter a valid phone number.",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                ClickableText(
                    text = annotatedText,
                    modifier = Modifier
                        .size(280.dp, 250.dp)
                        .alpha(0.5f), style = Typography.subtitle1,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "learn_more",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            /*TODO: 1. Open Learn More */
                        }
                    })

                GradientButton(onClick = {
                    if (!phoneNumber.isError && phoneNumber.text.isNotEmpty()) {
                        onClick(phoneNumber.text)
                        navHostController.navigate(Screen.VerifyScreen.withArgs(phoneNumber.text))
                    } else {
                        if (phoneNumber.text.isEmpty() || phoneNumber.text == "") {
                            Toast.makeText(
                                context,
                                "Field cannot be left blank.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter a valid phone number.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }) {
                    Text(text = "Next")
                }


            }

        }
    }
}


@Composable
fun PhoneNumberConsent(
    onPhoneNumberFetchedFromDevice: (phoneNumber: String) -> Unit,
) {
    val context = LocalContext.current

    val getPhoneNumberConsent =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK && result.data != null) {
                val credential = result.data!!.getParcelableExtra<Credential>(Credential.EXTRA_KEY)

                if (credential != null) {
//                    Timber.d("Phone number fetched from auto fill")
                    onPhoneNumberFetchedFromDevice(credential.id)
                }
            } else {
//                Timber.d("No number selected or unavailable. User can type manually.")
            }
        }

    LaunchedEffect(Unit) {
        val credentialsClient = Credentials.getClient(context)

        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        getPhoneNumberConsent.launch(IntentSenderRequest.Builder(intent.intentSender).build())
    }
}


