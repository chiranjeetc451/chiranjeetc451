package com.mindyug.app.presentation.dashboard

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.R
import com.mindyug.app.common.ProfilePictureState
import com.mindyug.app.common.util.getPrimaryKeyDate
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject
constructor(
    private val userDataRepository: UserDataRepository,
    private val statDataRepository: StatDataRepository,


    ) : ViewModel() {

    //    private val _refreshState = mutableStateOf(true)
    private var _refreshState = mutableStateOf(SwipeRefreshState(false))
    val refreshState: State<SwipeRefreshState> = _refreshState

    private val _profilePictureUri = mutableStateOf(
        ProfilePictureState()
    )

    val profilePictureUri: State<ProfilePictureState> = _profilePictureUri

    private val _appListGrid = mutableStateOf(
        AppListGrid()
    )
    val appListGrid: State<AppListGrid> = _appListGrid

    private var getAppStatsJob: Job? = null

    init {
        getProfilePictureUri(FirebaseAuth.getInstance().currentUser?.uid!!)
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

    fun getStatData(date: Date) {
        getAppStatsJob?.cancel()
        getAppStatsJob =
            statDataRepository.getStatDataByDate(getPrimaryKeyDate(date)).onEach { it2 ->
                it2.dailyUsedAppStatsList.sortByDescending { it.foregroundTime }
                _appListGrid.value = appListGrid.value.copy(
                    isLoading = false,
                    list = it2.dailyUsedAppStatsList,
                    totalTime = it2.dailyUsedAppStatsList.sumOf { sum ->
                        sum.foregroundTime
                    },
                )
            }.launchIn(viewModelScope)


    }

    fun delayFun() {
        viewModelScope.launch {
            _refreshState.value.isRefreshing = true
            delay(2000)
            _refreshState.value.isRefreshing = false

        }
    }

//    fun filterList(list: MutableList<AppStat>): MutableList<AppStat> {
//        val filteredList: MutableList<AppStat> = mutableListOf()
//        filteredList.add(list[0])
//        filteredList.add(list[1])
//        filteredList.add(list[2])
//        filteredList.add(list[3])
//        filteredList.add(list[4])
////        filteredList.add(list[5])
//        list.removeAt(0)
//        list.removeAt(1)
//        list.removeAt(2)
//        list.removeAt(3)
//        list.removeAt(4)
//
//        val fifthElement = AppStat("Others", list.sumOf { it.foregroundTime })
//        filteredList.add(fifthElement)
//
//        return filteredList
//    }


}