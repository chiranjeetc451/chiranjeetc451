package com.mindyug.app.presentation.login

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.common.MindYugButtonState
import com.mindyug.app.common.util.validateName
import com.mindyug.app.common.util.validateNumber
import com.mindyug.app.data.preferences.SharedPrefs
import com.mindyug.app.data.preferences.UserLoginState
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.Address
import com.mindyug.app.domain.model.UserData
import com.mindyug.app.domain.repository.UserDataRepository
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.utils.PointsReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository,
    private val userPreferences: UserLoginState,
    private val sharedPrefs: SharedPrefs,
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


    private val _btnNext = mutableStateOf(MindYugButtonState())
    val btnNext: State<MindYugButtonState> = _btnNext

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
        }
    }

    fun uploadProfilePic(uri: Uri) {
        userDataRepository.uploadProfilePic(uri)
    }

    fun addUser(
        enteredName: String,
        number: String,
        context: Context,
        navController: NavHostController
    ) {
        val user = UserData(enteredName, number, Address("", "", "", "", "", "", "", ""))
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
                    sharedPrefs.toggleLogin()
                    userPreferences.setUid(FirebaseAuth.getInstance().currentUser?.uid!!)
                    userPreferences.editName(enteredName)

                    startPointsReset(context)
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

                }
                is Results.Success -> {
                    if (result.data?.name != null) {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.HomeScreen.route)
                        }

                        sharedPrefs.toggleLogin()
                        userPreferences.setUid(FirebaseAuth.getInstance().currentUser?.uid!!)
                        userPreferences.editName(result.data.name)

                        startPointsReset(context)

                    } else {
                        navController.navigate(Screen.EnterNameScreen.withArgs(phone))
                    }

                    result.data?.let { Log.d("tag", it.name) }


                }
                is Results.Error -> {

                }

            }

        }.launchIn(viewModelScope)
    }

    private fun startPointsReset(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0

        val intent = Intent(context, PointsReceiver::class.java)
        val requestCode = 991
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            cal.timeInMillis,
            pendingIntent
        )
    }
}







