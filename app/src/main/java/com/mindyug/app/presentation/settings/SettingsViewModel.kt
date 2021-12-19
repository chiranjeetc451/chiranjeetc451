package com.mindyug.app.presentation.settings

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mindyug.app.R
import com.mindyug.app.common.ProfilePictureState
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.repository.UserDataRepository
import com.mindyug.app.presentation.login.MindYugTextFieldState
import com.mindyug.app.common.util.validateName
import com.mindyug.app.domain.model.Address
import com.mindyug.app.domain.model.UserData
import com.mindyug.app.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingsViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    private val _addressState = mutableStateOf(
        AddressState()
    )
    val addressState: State<AddressState> = _addressState


    private val _phoneNumber = mutableStateOf(
        MindYugTextFieldState(
            hint = "Phone Number"
        )
    )
    val phoneNumber: State<MindYugTextFieldState> = _phoneNumber

    private val _name = mutableStateOf(
        MindYugTextFieldState(
            hint = "Name"
        )
    )
    val name: State<MindYugTextFieldState> = _name

    private val _profilePictureUri = mutableStateOf(
        ProfilePictureState()
    )
    val profilePictureUri: State<ProfilePictureState> = _profilePictureUri

    private val _accountSettingsState = mutableStateOf(SettingsState())
    val accountSettingsState: State<SettingsState> = _accountSettingsState

    private val _btnSave = mutableStateOf(MindYugSettingsButtonState())
    val btnSave: State<MindYugSettingsButtonState> = _btnSave


    private val _btnAddressSave = mutableStateOf(MindYugSettingsButtonState())
    val btnAddressSave: State<MindYugSettingsButtonState> = _btnAddressSave

    private val _addressLineOne = mutableStateOf(
        MindYugTextFieldState(
            hint = "Address Line 1"
        )
    )
    val addressLineOne: State<MindYugTextFieldState> = _addressLineOne

    private val _addressLineTwo = mutableStateOf(
        MindYugTextFieldState(
            hint = "Address Line 2"
        )
    )
    val addressLineTwo: State<MindYugTextFieldState> = _addressLineTwo

    private val _houseNo = mutableStateOf(
        MindYugTextFieldState(
            hint = "House No"
        )
    )
    val houseNo: State<MindYugTextFieldState> = _houseNo

    private val _landmark = mutableStateOf(
        MindYugTextFieldState(
            hint = "Landmark"
        )
    )
    val landmark: State<MindYugTextFieldState> = _landmark

    private val _city = mutableStateOf(
        MindYugTextFieldState(
            hint = "City"
        )
    )
    val city: State<MindYugTextFieldState> = _city

    private val _pincode = mutableStateOf(
        MindYugTextFieldState(
            hint = "Pin Code"
        )
    )
    val pincode: State<MindYugTextFieldState> = _pincode

    private val _state = mutableStateOf(
        MindYugTextFieldState(
            hint = "State"
        )
    )
    val state: State<MindYugTextFieldState> = _state


    private val _country = mutableStateOf(
        MindYugTextFieldState(
            hint = "State"
        )
    )
    val country: State<MindYugTextFieldState> = _country




    fun getProfilePictureUri(uid: String) {
        userDataRepository.getProfilePictureUri(uid).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    _profilePictureUri.value = profilePictureUri.value.copy(
                        uri = Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}")
                    )
                }
                is Results.Success -> {
                    _profilePictureUri.value = profilePictureUri.value.copy(
                        uri = result.data
                    )
                }
                is Results.Error -> {
                    _profilePictureUri.value = profilePictureUri.value.copy(
                        uri = Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}")
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    fun changeToNewProfilePic(uri: Uri) {
        _profilePictureUri.value = profilePictureUri.value.copy(
            uri = uri
        )
        _btnSave.value = btnSave.value.copy(
            isEnabled = true
        )

    }

    fun uploadProfilePicFromUid(uid: String) {
        profilePictureUri.value.uri?.let { userDataRepository.updateProfilePictureFromUid(it, uid) }
    }

    fun getUser(uid: String) {
        userDataRepository.getUserFromUid(uid).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    _accountSettingsState.value = accountSettingsState.value.copy(
                        isLoading = true
                    )
                }
                is Results.Success -> {
                    _accountSettingsState.value = accountSettingsState.value.copy(
                        isLoading = false
                    )
                    _phoneNumber.value = phoneNumber.value.copy(
                        text = result.data!!.number,
                        isError = false

                    )
                    _name.value = name.value.copy(
                        text = result.data.name,
                        isError = false

                    )

                    _addressLineOne.value = addressLineOne.value.copy(
                        text = result.data.address!!.addressLineOne,
                        isError = false
                    )
                    _addressLineTwo.value = addressLineTwo.value.copy(
                        text = result.data.address.addressLineTwo!!,
                        isError = false
                    )
                    _houseNo.value = houseNo.value.copy(
                        text = result.data.address!!.houseNo!!,
                        isError = false
                    )
                    _landmark.value = landmark.value.copy(
                        text = result.data!!.address!!.landmark!!,
                        isError = false
                    )
                    _city.value = city.value.copy(
                        text = result.data!!.address!!.city!!,
                        isError = false
                    )

                    _state.value = state.value.copy(
                        text = result.data!!.address!!.state!!,
                        isError = false
                    )

                    _pincode.value = pincode.value.copy(
                        text = result.data!!.address!!.pinCode!!,
                        isError = false
                    )




                    _country.value = country.value.copy(
                        text = result.data.address.country,
                        isError = false
                    )


                    _addressState.value = addressState.value.copy(
                        houseNo = result.data.address!!.houseNo!!,
                        addressLineOne = result.data!!.address!!.addressLineOne,
                        addressLineTwo = result.data!!.address!!.addressLineTwo!!,
                        pinCode = result.data!!.address!!.pinCode!!,
                        state = result.data!!.address!!.state!!,
                        city = result.data!!.address!!.city!!,
                        country = result.data.address.country,
                        landmark = result.data.address.landmark
                    )
                }
                is Results.Error -> {
                    _accountSettingsState.value = accountSettingsState.value.copy(
                        isLoading = true
                    )
                }

            }

        }.launchIn(viewModelScope)
    }

    fun editName(newName: String, oldName: String) {
        _name.value = name.value.copy(
            text = newName,
            isError = false

        )
        if (!validateName(name.value.text)) {
            _name.value = name.value.copy(
                isError = true
            )
        }
        if (name.value.text != oldName) {
            _btnSave.value = btnSave.value.copy(
                isEnabled = true
            )
        }

    }

    fun onEvent(event: AddressEvent) {
        when (event) {
            is AddressEvent.EnteredAddressLineOne -> {
                _addressLineOne.value = addressLineOne.value.copy(
                    text = event.value,
                    isError = false
                )

                if (addressLineOne.value.text != addressState.value.addressLineOne) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }
            }
            is AddressEvent.EnteredAddressLineTwo -> {
                _addressLineTwo.value = addressLineTwo.value.copy(
                    text = event.value,
                    isError = false
                )

                if (addressLineTwo.value.text != addressState.value.addressLineTwo) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }
            }
            is AddressEvent.EnteredHouseNo -> {
                _houseNo.value = houseNo.value.copy(
                    text = event.value,
                    isError = false
                )
                if (houseNo.value.text != addressState.value.houseNo) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }
            }
            is AddressEvent.EnteredLandmark -> {
                _landmark.value = landmark.value.copy(
                    text = event.value,
                    isError = false
                )
                if (landmark.value.text != addressState.value.landmark) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }

            }
            is AddressEvent.EnteredCity -> {
                _city.value = city.value.copy(
                    text = event.value,
                    isError = false
                )
                if (city.value.text != addressState.value.city) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }

            }
            is AddressEvent.EnteredState -> {
                _state.value = state.value.copy(
                    text = event.value,
                    isError = false
                )
                if (state.value.text != addressState.value.state) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }
            }
            is AddressEvent.EnteredCountry -> {
                _country.value = country.value.copy(
                    text = event.value,
                    isError = false
                )
                if (country.value.text != addressState.value.country) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }

            }
            is AddressEvent.EnteredPinCode -> {
                _pincode.value = pincode.value.copy(
                    text = event.value,
                    isError = false
                )
                if (pincode.value.text != addressState.value.pinCode) {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true
                    )
                }
            }
        }
    }

    fun updateUserData(
        uid: String,
        enteredName: String,
        number: String,
        houseNo: String?,
        addressLineOne: String,
        addressLineTwo: String?,
        pinCode: String,
        landmark: String?,
        city: String,
        state: String,
        country: String,
        navController: NavHostController
    ) {
        val user = UserData(
            enteredName,
            number,
            Address(
                houseNo,
                addressLineOne,
                addressLineTwo,
                pinCode,
                landmark,
                city,
                state,
                country
            )
        )

        userDataRepository.setUserDataFromUid(user, uid).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = false,
                        isLoading = true,
                    )
                }
                is Results.Success -> {
                    _btnAddressSave.value = btnAddressSave.value.copy(
                        isEnabled = true,
                        isLoading = false,
                    )
                    navController.navigateUp()

                }
                is Results.Error -> {

                }

            }

        }.launchIn(viewModelScope)


    }


}