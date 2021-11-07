package com.mindyug.app.presentation.util

sealed class Screen(val route: String) {

    object IntroductionScreen : Screen(route = "introduction_screen")


    object EnterNumberScreen : Screen(route = "enter_number_screen")
    object VerifyScreen : Screen(route = "verify_screen")
    object EnterNameScreen : Screen(route = "enter_name_screen")
    object EnterUsernameScreen : Screen(route = "enter_username_screen")
    object UploadPhotoScreen : Screen(route = "upload_photo_screen")


    object HomeScreen : Screen(route = "home_screen")

    object Dashboard : Screen("home/dashboard")
    object Rewards : Screen("home/rewards")

    object SettingsScreen : Screen("settings")
    object AccountSettings:Screen("account_settings")
    object Address:Screen("address")




    object ProfileScreen : Screen("profile")
    object NotificationsScreen : Screen("notifications")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}


