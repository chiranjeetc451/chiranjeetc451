package com.mindyug.app.presentation.login

data class UserDataState(
    val phoneNumber: String = "",
    val otp: String = "",
    val name: String = "",
    val username: String = "",
    val profilePicUri: String = "",
)
