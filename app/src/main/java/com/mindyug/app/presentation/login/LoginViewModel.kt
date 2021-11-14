package com.mindyug.app.presentation.login

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.common.MindYugButtonState
import com.mindyug.app.common.util.validateName
import com.mindyug.app.common.util.validateNumber
import com.mindyug.app.common.util.validateUsername
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.Address
import com.mindyug.app.domain.model.UserData
import com.mindyug.app.domain.repository.UserDataRepository
import com.mindyug.app.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    private val _state = mutableStateOf(UserDataState())
    val state: State<UserDataState> = _state

    private val _phoneNumber = mutableStateOf(
        MindYugTextFieldState(
            hint = "Phone Number"
        )
    )
    val phoneNumber: State<MindYugTextFieldState> = _phoneNumber

    private val _otp = mutableStateOf(MindYugTextFieldState())
    val otp: State<MindYugTextFieldState> = _otp

    private val _name = mutableStateOf(
        MindYugTextFieldState(
            hint = "Enter your name"
        )
    )
    val name: State<MindYugTextFieldState> = _name

    private val _username = mutableStateOf(
        MindYugTextFieldState(
            hint = "Username"
        )
    )
    val username: State<MindYugTextFieldState> = _username

    private val _btnNext = mutableStateOf(MindYugButtonState())
    val btnNext: State<MindYugButtonState> = _btnNext

    private val _btnVerify = mutableStateOf(MindYugButtonState())
    val btnVerify: State<MindYugButtonState> = _btnVerify


    fun onEvent(event: UserDataEvent) {
        when (event) {
            is UserDataEvent.EnteredPhoneNumber -> {
                _phoneNumber.value = phoneNumber.value.copy(
                    text = event.value,
                    isError = false
                )
                if (!validateNumber(phoneNumber.value.text)) {
                    _phoneNumber.value = phoneNumber.value.copy(
                        isError = true
                    )

                }
            }
            is UserDataEvent.EnteredOTP -> {
                _otp.value = otp.value.copy(
                    text = event.value
                )
            }

            is UserDataEvent.EnteredName -> {
                _name.value = name.value.copy(
                    text = event.value,
                    isError = false

                )
                if (!validateName(name.value.text)) {
                    _name.value = name.value.copy(
                        isError = true
                    )

                }
            }
            is UserDataEvent.EnteredUsername -> {
                _username.value = username.value.copy(
                    text = event.value,
                    isError = false
                )
                if (!validateUsername(username.value.text)) {
                    _username.value = username.value.copy(
                        isError = true
                    )
                }
            }
        }
    }


    fun uploadProfilePic(uri: Uri) {
        userDataRepository.uploadProfilePic(uri)
    }

    fun addUser(
        enteredName: String,
        username: String,
        number: String,
        context: Context,
        navController: NavHostController
    ) {
        val user = UserData(enteredName, username, number, Address("", "", "", "", "", "", "", ""))
        userDataRepository.setUserData(user).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    _btnNext.value = btnNext.value.copy(
                        isEnabled = false,
                        isLoading = true,
                    )
                }
                is Results.Success -> {
                    navController.navigate(Screen.HomeScreen.route)
                    val prefs = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
                        ?: return@onEach
                    with(prefs.edit()) {
                        putBoolean("isUserLoggedIn", true)
                        putString("uid", FirebaseAuth.getInstance().currentUser?.uid!!)
                        putString("name", enteredName)
                        apply()
                    }
                    Toast.makeText(
                        context,
                        "Verification Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Results.Error -> {
                    _btnNext.value = btnNext.value.copy(
                        error = "Unknown error occurred. Please check your internet connection.",
                        isEnabled = true,
                    )
                }

            }

        }.launchIn(viewModelScope)
    }

    fun getUsername(context: Context, navController: NavHostController, phone: String) {
        userDataRepository.getUsernameFromUid().onEach { result ->
            when (result) {
                is Results.Loading -> {
                    Toast.makeText(
                        context,
                        "wait",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Results.Success -> {
//                    return@onEach result.data?.username != null
                    if (result.data?.username != null) {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.HomeScreen.route)
                        }

                        val prefs = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
                            ?: return@onEach
                        with(prefs.edit()) {
                            putBoolean("isUserLoggedIn", true)
                            putString("uid", FirebaseAuth.getInstance().currentUser?.uid!!)
                            putString("name", result.data.name)
                            apply()
                        }

                    } else {
                        navController.navigate(Screen.EnterNameScreen.withArgs(phone))
                    }

                    result.data?.let { Log.d("tag", it.username) }
                    Toast.makeText(
                        context,
                        "Verification ooop ${result.data?.username}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Results.Error -> {
                    Toast.makeText(
                        context,
                        "error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }.launchIn(viewModelScope)
    }


}







