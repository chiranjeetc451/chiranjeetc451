package com.mindyug.app.presentation.points

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyug.app.common.MindYugButtonState
import com.mindyug.app.data.preferences.PointSysUtils
import com.mindyug.app.data.preferences.UserLoginState
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.repository.PointRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel
@Inject constructor(
    private val pointRepository: PointRepository,
    private val userPreferences: UserLoginState,
    private val pointSysUtils: PointSysUtils
) : ViewModel() {

    private val _pointList = mutableStateOf(
        PointListState()
    )
    val pointList: State<PointListState> = _pointList

    private val _collectBtn = mutableStateOf(
        MindYugButtonState()
    )
    val collectBtn: State<MindYugButtonState> = _collectBtn

    private val _uid = mutableStateOf("")
    val uid: State<String> = _uid


    private val _temporaryPoints = mutableStateOf<Long>(0)
    val temporaryPoints: State<Long> = _temporaryPoints

    init {
        loadPoints()
        loadUid()
        getButtonState()
        loadTemporaryPoints()
    }

    private fun loadUid() {
        viewModelScope.launch {
            userPreferences.uid.collect {
                _uid.value = it
            }

        }
    }

    private fun getPoints(uid: String) {
        pointRepository.getAllPointsFromUid(uid).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    _pointList.value = pointList.value.copy(
                        isLoading = true
                    )
                }
                is Results.Success -> {
                    _pointList.value = pointList.value.copy(
                        isLoading = false,
                        list = result.data?.reversed()!!,
                        points = result.data!!.sumOf { it.points }
                    )
                }
                is Results.Error -> {
                    // put a SnackBar
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadPoints() {
        viewModelScope.launch {
            userPreferences.uid.collect {
                getPoints(it)
            }
        }
    }

    private fun loadTemporaryPoints() {
        viewModelScope.launch {
            pointSysUtils.temporaryPoints.collect {
                _temporaryPoints.value = it
            }
        }
    }

    fun addPoint(
        pointItem: PointItem,
        context: Context,
    ) {
        pointRepository.savePointItem(pointItem, _uid.value).onEach { result ->
            when (result) {
                is Results.Loading -> {
                    Toast.makeText(
                        context,
                        "Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Results.Success -> {
                    Toast.makeText(
                        context,
                        "Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Results.Error -> {
                    Toast.makeText(
                        context,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }.launchIn(viewModelScope)
    }



    private fun getButtonState() {
        viewModelScope.launch {
            pointSysUtils.collectButtonState.collect {
                _collectBtn.value.isEnabled = it
            }
        }
    }



    fun toggleButtonState() {
        viewModelScope.launch {
            pointSysUtils.toggleButtonState()
        }
    }

    fun clear(){
        viewModelScope.launch {
            pointSysUtils.clearPoints()
        }
    }




}