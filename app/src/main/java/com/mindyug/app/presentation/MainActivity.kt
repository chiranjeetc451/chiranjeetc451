package com.mindyug.app.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mindyug.app.presentation.dashboard.Dashboard
import com.mindyug.app.presentation.home.MindYugBottomNavigationBar
import com.mindyug.app.presentation.rewards.Rewards
import com.mindyug.app.presentation.introduction.IntroductionScreen
import com.mindyug.app.presentation.login.*
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private val mAuth = FirebaseAuth.getInstance()
    var verificationOtp = ""

    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }

    private fun send(mobileNum: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+91$mobileNum")
            .setTimeout(0, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(applicationContext, "Verification Completed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext, "Verification Failed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    verificationOtp = otp
                    Toast.makeText(applicationContext, "Otp Send Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun otpVerification(otp: String, phone: String, navController: NavHostController) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    viewModel.getUsername(applicationContext, navController, phone)

//                    Toast.makeText(
//                        applicationContext,
//                        "Verification Successful $username",
//                        Toast.LENGTH_SHORT
//                    ).show()

                } else {
                    Toast.makeText(applicationContext, "Wrong Otp", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun resend(
        phone: String,
    ) {
        var token: PhoneAuthProvider.ForceResendingToken? = null
        val optionsBuilder = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+91$phone")
            .setTimeout(0, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(applicationContext, "Verification Completed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext, "Verification Failed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    verificationOtp = otp
                    token = p1
                    Toast.makeText(applicationContext, "Otp Send Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        if (token != null) {
            optionsBuilder.setForceResendingToken(token!!) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())

    }

    @ExperimentalCoilApi
    @Composable
    fun Navigation() {
        val context = LocalContext.current
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = if (getUserLoggedInState(context)) {
                Screen.HomeScreen.route
            } else {
                Screen.IntroductionScreen.route

            }
        ) {
            composable(
                route = Screen.IntroductionScreen.route,
            ) {
                IntroductionScreen(navController = navController)
            }
            composable(
                route = Screen.EnterNumberScreen.route,
            ) {
                EnterNumberScreen(
                    navHostController = navController,
                ) { mobileNum ->
                    send(mobileNum)
                }
            }
            composable(
                route = Screen.VerifyScreen.route + "/{number}",
                arguments = listOf(navArgument("number") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { entry ->
                entry.arguments?.getString("number")?.let {
                    VerifyScreen(
                        navHostController = navController,
                        number = it,
                        onClick = { otp ->
                            otpVerification(otp, it, navController)
                        },
                        onResendClick = { mobileNum ->
                            resend(mobileNum)

                        }
                    )
                }
            }
            composable(
                route = Screen.EnterNameScreen.route + "/{number}",
                arguments = listOf(navArgument("number") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { entry ->
                entry.arguments?.getString("number")?.let {
                    EnterNameScreen(navHostController = navController, verifiedNumber = it)
                }
            }
            composable(
                route = Screen.EnterUsernameScreen.route + "/{number}/{name}",
                arguments = listOf(navArgument("number") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }, navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { entry ->
                entry.arguments?.getString("number")?.let {
                    EnterUsernameScreen(
                        navHostController = navController,
                        number = it,
                        name = entry.arguments?.getString("name")!!
                    )
                }
            }
            composable(
                route = Screen.UploadPhotoScreen.route + "/{number}/{name}/{username}",
                arguments = listOf(navArgument("number") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }, navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }, navArgument("username") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { entry ->
                UploadPhotoScreen(
                    navHostController = navController,
                    number = entry.arguments?.getString("number")!!,
                    name = entry.arguments?.getString("name")!!,
                    username = entry.arguments?.getString("username")!!,
                )
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
        }
    }

    private fun getUserLoggedInState(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
        return sharedPref.getBoolean("isUserLoggedIn", false)
    }

    @ExperimentalCoilApi
    @Composable
    fun HomeScreen() {
        val navInnerController = rememberNavController()
        MindYugTheme {
            Scaffold(
                bottomBar = { MindYugBottomNavigationBar(navController = navInnerController) }
            ) {

                NavHost(
                    navController = navInnerController, startDestination = Screen.Dashboard.route
                ) {
                    composable(route = Screen.Dashboard.route) {
                        Dashboard()
                    }
                    composable(route = Screen.Rewards.route) {
                        Rewards()
                    }
                }


            }
        }
    }
}












