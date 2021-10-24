package com.mindyug.app.presentation.login

import android.app.Activity
import android.content.*
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.login.components.OTPTextFields
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography


@Composable
fun VerifyScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    number: String,
    onClick: (otp: String) -> Unit,
) {
    val otp = viewModel.otp.value
    val context = LocalContext.current


    SmsRetrieverUserConsentBroadcast { _, code ->
        onClick(code)
    }


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
            val phoneNumber = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFFFFFFFF))) {
                    append("$number      ")
                }
                pushStringAnnotation(
                    tag = "resend",// provide tag which will then be provided when you click the text
                    annotation = "resend"
                )
                withStyle(style = SpanStyle(color = Color(0xFFB5B4ED))) {
                    append("RESEND")
                }
                pop()

            }
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Verify",
                    style = Typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))

                OTPTextFields(
                    length = 6
                ) { getOpt ->
                    viewModel.onEvent(UserDataEvent.EnteredOTP(getOpt))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Please enter the OTP sent to:")

                ClickableText(text = phoneNumber, onClick = { offset ->
                    phoneNumber.getStringAnnotations(
                        tag = "resend",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
//                        Log.d("tag", "clicked")
                    }
                })

                Spacer(modifier = Modifier.height(24.dp))

                GradientButton(onClick = {
                    if (otp.text.isNotEmpty()) {
                        onClick(otp.text)
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter a valid OTP",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }) {
                    Text(text = "Next")
                }

            }
        }
    }

}


@Composable
fun SmsRetrieverUserConsentBroadcast(
    smsCodeLength: Int = 6,
    onSmsReceived: (message: String, code: String) -> Unit,
) {
    val context = LocalContext.current

    var shouldRegisterReceiver by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
//        Timber.d("Initializing Sms Retriever client")
        SmsRetriever.getClient(context)
            .startSmsUserConsent(null)
            .addOnSuccessListener {
//                Timber.d("SmsRetriever started successfully")
                shouldRegisterReceiver = true
            }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it?.resultCode == Activity.RESULT_OK && it.data != null) {
                val message: String? = it.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                message?.let {
//                Timber.d("Sms received: $message")
                    val verificationCode = getVerificationCodeFromSms(message, smsCodeLength)
//                Timber.d("Verification code parsed: $verificationCode")

                    onSmsReceived(message, verificationCode)
                }
                shouldRegisterReceiver = false
            } else {
//            Timber.d("Consent denied. User can type OTC manually.")
            }
        }

    if (shouldRegisterReceiver) {
        SystemBroadcastReceiver(
            broadCastPermission = SmsRetriever.SEND_PERMISSION,
            systemAction = SmsRetriever.SMS_RETRIEVED_ACTION,
        ) { intent ->
            if (intent != null && SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras

                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent =
                            extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            launcher.launch(consentIntent)
                        } catch (e: ActivityNotFoundException) {
//                            Timber.e(e, "Activity Not found for SMS consent API")
                        }
                    }

                    CommonStatusCodes.TIMEOUT -> {


                    }
                }
            }
        }
    }
}

@Composable
fun SystemBroadcastReceiver(
    broadCastPermission: String,
    systemAction: String,
    onSystemEvent: (intent: Intent?) -> Unit
) {
    // Grab the current context in this part of the UI tree
    val context = LocalContext.current

    // Safely use the latest onSystemEvent lambda passed to the function
    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)

    // If either context or systemAction changes, unregister and register again
    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val intentFilter2 = IntentFilter(broadCastPermission)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onSystemEvent(intent)
            }
        }

        context.registerReceiver(broadcast, intentFilter2)
        context.registerReceiver(broadcast, intentFilter)

        // When the effect leaves the Composition, remove the callback
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }


}


internal fun getVerificationCodeFromSms(sms: String, smsCodeLength: Int): String =
    sms.filter { it.isDigit() }
        .substring(0 until smsCodeLength)



