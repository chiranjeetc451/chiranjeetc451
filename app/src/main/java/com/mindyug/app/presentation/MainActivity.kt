package com.mindyug.app.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mindyug.app.presentation.home.Dashboard
import com.mindyug.app.presentation.home.Rewards
import com.mindyug.app.presentation.introduction.IntroductionScreen
import com.mindyug.app.presentation.login.*
import com.mindyug.app.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    var verificationOtp = ""


    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberAnimatedNavController()
            val backstackEntry = navController.currentBackStackEntryAsState()
            MindYugAppContent(navController = navController)
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
                    Toast.makeText(
                        applicationContext,
                        "Verification Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(Screen.EnterNameScreen.withArgs(phone))
                } else {
                    Toast.makeText(applicationContext, "Wrong Otp", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @ExperimentalAnimationApi
    @Composable
    fun MindYugAppContent(navController: NavHostController) {
        val context = LocalContext.current
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.IntroductionScreen.route
        ) {
            composable(
                route = Screen.IntroductionScreen.route,
                exitTransition = { _, _ ->
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),

                        ) + fadeOut(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    ) +
                            fadeIn(animationSpec = tween(durationMillis = 300))
                },
            ) {
                IntroductionScreen(onNextClick = {
                    navController.navigate(Screen.EnterNumberScreen.route)
                })
            }
            composable(
                route = Screen.EnterNumberScreen.route,

//                enterTransition = { _, _ ->
//                    slideInHorizontally(
//                        initialOffsetX = { 300 },
//                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
//                    ) +
//                            fadeIn(animationSpec = tween(durationMillis = 300))
//                },
//                popExitTransition = { _, _ ->
//                    slideOutHorizontally(
//                        targetOffsetX = { 300 },
//                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
//
//                        ) + fadeOut(animationSpec = tween(durationMillis = 300))
//
//
//                },
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
                        navHostController = navController, number = it,
                    ) { otp ->
                        otpVerification(otp, it, navController)

                    }
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
                route = Screen.UploadPhotoScreen.route+ "/{number}/{name}/{username}",
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

            composable(route = Screen.Dashboard.route) {
                Dashboard()
            }
            composable(route = Screen.Rewards.route) {
                Rewards()
            }
        }
    }


}










