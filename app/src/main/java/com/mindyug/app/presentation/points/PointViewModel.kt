package com.mindyug.app.presentation.points

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.R
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.repository.PointRepository
import com.mindyug.app.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PointViewModel
@Inject constructor(
    private val pointRepository: PointRepository
) : ViewModel() {

    private val _pointList = mutableStateOf(
        PointListState()
    )
    val pointList: State<PointListState> = _pointList

    fun getPoints(uid: String, context: Context) {
        pointRepository.getAllPointsFromUid(uid).onEach { result->
            when (result) {
                is Results.Loading -> {
                    _pointList.value = pointList.value.copy(
                        isLoading = true
                    )
                    Toast.makeText(
                        context,
                        "Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Results.Success -> {
                    _pointList.value = pointList.value.copy(
                        isLoading = false,
                        list = result.data!!,
                        points = result.data!!.sumOf { it.points }
                    )
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


    fun addPoint(
        pointItem: PointItem,
        uid: String,
        context: Context,
    ) {
        pointRepository.savePointItem(pointItem, uid).onEach { result ->
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


}