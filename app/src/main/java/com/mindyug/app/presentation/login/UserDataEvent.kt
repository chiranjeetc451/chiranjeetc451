package com.mindyug.app.presentation.login

sealed class UserDataEvent {
    data class EnteredPhoneNumber(val value: String) : UserDataEvent()
    data class EnteredOTP(val value: String) : UserDataEvent()
    data class EnteredName(val value: String) : UserDataEvent()
}