package com.mindyug.app.presentation

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mindyug.app.common.StatisticsViewModel
import com.mindyug.app.common.util.getDateFromDate
import com.mindyug.app.common.util.getMonthFromDate
import com.mindyug.app.common.util.getPrimaryKeyDate
import com.mindyug.app.common.util.getYearFromDate
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.presentation.dashboard.Dashboard
import com.mindyug.app.presentation.home.RequestPermissionScreen
import com.mindyug.app.presentation.home.components.SheetContent
import com.mindyug.app.presentation.introduction.IntroductionScreen
import com.mindyug.app.presentation.login.*
import com.mindyug.app.presentation.notifications.NotificationsScreen
import com.mindyug.app.presentation.points.PointsScreen
import com.mindyug.app.presentation.profile.ProfileScreen
import com.mindyug.app.presentation.rewards.Rewards
import com.mindyug.app.presentation.settings.AccountSettings
import com.mindyug.app.presentation.settings.AddressScreen
import com.mindyug.app.presentation.settings.SettingsScreen
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private val statViewModel: StatisticsViewModel by viewModels()
    var date: Date = Date()


    private val mAuth = FirebaseAuth.getInstance()
    var verificationOtp = ""

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statViewModel.loadStatData(
            getPurifiedList(),
            getDateFromDate(Date()),
            getMonthFromDate(Date()),
            getYearFromDate(Date()),
            getPrimaryKeyDate(Date())
        )


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

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
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
                if (!userGrantsPermission()) {
                    RequestPermissionScreen(
                        onClick =
                        {
                            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                        }
                    )
                } else {
                    HomeScreen(navController)

                }
            }
            composable(route = Screen.SettingsScreen.route) {
                SettingsScreen(navController)
            }
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen(navController)
            }
            composable(route = Screen.NotificationsScreen.route) {
                NotificationsScreen(navController)
            }
            composable(route = Screen.AccountSettings.route) {
                AccountSettings(navController)
            }
            composable(route = Screen.Address.route) {
                AddressScreen(navController)
            }
        }
    }

    private fun getUserLoggedInState(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
        return sharedPref.getBoolean("isUserLoggedIn", false)
    }

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalCoilApi
    @Composable
    fun HomeScreen(navController: NavHostController) {
        val navInnerController = rememberNavController()


        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )


        MindYugTheme {

            BottomSheetScaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                sheetContent = {
                        PointsScreen(
                            navController = navInnerController,
                            elevation = if (scaffoldState.bottomSheetState.isCollapsed) {
                                8.dp
                            } else {
                                0.dp
                            },
                            isEnabled = scaffoldState.bottomSheetState.isCollapsed,
                        )

                },
                sheetPeekHeight = 60.dp,
            ) {
                NavHost(
                    navController = navInnerController, startDestination = Screen.Dashboard.route
                ) {
                    composable(route = Screen.Dashboard.route) {
                        Dashboard(navController = navController)
                    }
                    composable(route = Screen.Rewards.route) {
                        Rewards()
                    }
                }
            }

        }
    }


    fun getPurifiedList(): MutableList<AppStat> {
        val list = getStatsList()    // return a list of events
        val list2 = appList()        // return a list of installed apps
        val googlePackages = mutableListOf(
            "com.android.chrome",
            "com.google.android.youtube",
            "com.google.android.gm",
            "com.google.android.apps.maps",
            "com.google.android.googlequicksearchbox",
            "com.google.android.apps.photos",
            "com.google.android.apps.maps",
            "com.google.android.apps.tachyon",
            "com.google.android.apps.youtube.music",
            "com.google.android.apps.docs",
            "com.google.android.apps.googleassistant",
            "com.google.android.apps.nbu.files",
            "com.google.android.apps.messaging",
            "com.google.android.calendar",
            "com.google.android.keep",
        )
        for (name in googlePackages) {
            val ai: ApplicationInfo = try {
                this.packageManager.getApplicationInfo(
                    name,
                    PackageManager.GET_META_DATA
                )
            } catch (e: Exception) {
                continue
            }
            list2.add(ai)
        }
        val purifiedList: MutableList<AppStat> = mutableListOf()
        for (name in list2) {
//            Log.d("tag", "package: ${app.name}, time: ${app.time} now111")
//            Log.d("tag","package: ${app.packageName}")
            var n: Long = 0
            for (app in list) {
                if (app.packageName == name.packageName) {
                    n += app.foregroundTime
                }
            }
            purifiedList.add(AppStat(name.packageName, n))
        }
        for (app in purifiedList) {
            if (app.foregroundTime.equals(0)) {
                purifiedList.remove(app)
            }
        }
        return purifiedList
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getStatsList(): MutableList<AppStat> {
        val usm: UsageStatsManager =
            (this.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager)
//        val appList = ArrayList<App>()
        val appList: MutableList<AppStat> = mutableListOf()
        val cal = java.util.Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        val startTime = cal.timeInMillis
        val endTime = System.currentTimeMillis()
//        val allEvents = ArrayList<UsageEvents.Event>()
        val allEvents: MutableList<UsageEvents.Event> = mutableListOf()
        val usageEvents: UsageEvents = usm.queryEvents(startTime, endTime)
        var event: UsageEvents.Event
        var app: AppStat?
        while (usageEvents.hasNextEvent()) {
            event = UsageEvents.Event()
            usageEvents.getNextEvent(event)
            if (event.eventType == 1 || event.eventType == 2) {
                allEvents.add(event)
            }
        }

//        Collections.sort(allEvents, sortEv())
        for (i in 1 until allEvents.size) {
            val event0 = allEvents[i]
            val event1 = allEvents[i - 1]
            if (event1.eventType == 1 && event0.eventType == 2 && event1.packageName == event0.packageName) {
//                icon = packageManager.getApplicationIcon(E1.packageName)
                val diff = event0.timeStamp - event1.timeStamp
                app = AppStat(
//                    icon,
                    event1.packageName,
                    diff
                )
                appList.add(app)

            }
        }

        return appList
    }


    @SuppressLint("QueryPermissionsNeeded")
    fun appList(): MutableList<ApplicationInfo> {

        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA).filterNot {
            (it.flags and ApplicationInfo.FLAG_SYSTEM) != 0
            //            packageManager.getLeanbackLaunchIntentForPackage(it.packageName) != null

        } as MutableList<ApplicationInfo>
    }

    private fun userGrantsPermission(): Boolean {
        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

}











