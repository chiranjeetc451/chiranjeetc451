package com.mindyug.app.presentation.dashboard

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyug.app.R
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository,
    private val statDataRepository: StatDataRepository

) : ViewModel() {

    private val _profilePictureUri = mutableStateOf(
        ProfilePictureState()
    )

    val profilePictureUri: State<ProfilePictureState> = _profilePictureUri

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

    fun getStatData(date: Date): List<AppStat>? {
        var data: StatData? = null
        viewModelScope.launch {
            data = statDataRepository.getStatDataByDate(date)
        }
        return data?.dailyUsedAppStatsList
    }

}