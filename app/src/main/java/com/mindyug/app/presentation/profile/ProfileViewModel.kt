package com.mindyug.app.presentation.profile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyug.app.R
import com.mindyug.app.common.ProfilePictureState
import com.mindyug.app.data.preferences.UserLoginState
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository,
    private val statDataRepository: StatDataRepository,
    private val userPreferences: UserLoginState
) : ViewModel() {

    private val _profilePictureUri = mutableStateOf(
        ProfilePictureState()
    )

    val profilePictureUri: State<ProfilePictureState> = _profilePictureUri

    private val _listState = mutableStateOf(
        StatDataList()
    )
    val listState: State<StatDataList> = _listState

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private var getAppStatsJob: Job? = null

    init {
        getAllEntries()
        loadProfile()
        loadName()

    }

    private fun loadName() {
        viewModelScope.launch {
            userPreferences.name.collect {
                _name.value = it
            }

        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            userPreferences.uid.collect {
                getProfilePictureUri(it)
            }
        }
    }


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

    private fun getAllEntries() {
        getAppStatsJob?.cancel()

        getAppStatsJob = statDataRepository.getAllEntries().onEach { it2 ->

            _listState.value = listState.value.copy(
                isLoading = false,
                list = it2.reversed()
            )
        }.launchIn(viewModelScope)
    }

}