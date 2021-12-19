package com.mindyug.app.presentation.points

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mindyug.app.common.MindYugButtonState
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.repository.PointRepository
import com.mindyug.app.background.PointCollectWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.xml.datatype.DatatypeConstants.HOURS

@HiltViewModel
class PointViewModel
@Inject constructor(
    private val pointRepository: PointRepository,
    private val workManager: WorkManager

) : ViewModel() {

    private val _pointList = mutableStateOf(
        PointListState()
    )
    val pointList: State<PointListState> = _pointList

    private val _collectBtn = mutableStateOf(
        MindYugButtonState()
    )

    val collectBtn: State<MindYugButtonState> = _collectBtn

    fun getPoints(uid: String, context: Context) {
        pointRepository.getAllPointsFromUid(uid).onEach { result ->
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
                        list = result.data?.reversed()!!,
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

    fun pointsReset() {
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<PointCollectWorker>()
                .setInitialDelay(
                    24,
                    TimeUnit.HOURS
                )
                .addTag("PointResetWork")
                .build()

        workManager.cancelAllWorkByTag("PointResetWork")

        workManager.enqueue(uploadWorkRequest)
    }

    fun setBtnState(state: Boolean) {
        _collectBtn.value = collectBtn.value.copy(isEnabled = state)
    }



}