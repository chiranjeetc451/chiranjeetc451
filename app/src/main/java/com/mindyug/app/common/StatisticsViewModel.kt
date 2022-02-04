package com.mindyug.app.common

import android.icu.util.Calendar
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyug.app.common.util.monthFromDateInString
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject
constructor(
    private val statDataRepository: StatDataRepository
) : ViewModel() {

    private val _points = mutableStateOf<Long>(0)
    val points: State<Long> = _points

    private var getAppStatsJob: Job? = null


    fun loadStatData(
        list: MutableList<AppStat>,
        date: String,
        month: String,
        year: String,
        loggedDate: String
    ) {
        viewModelScope.launch {
            statDataRepository.setStatData(StatData(list, date, month, year, loggedDate))
        }
    }

    init {
        getTotalPoints()
    }


    private fun getTotalPoints() {
        getAppStatsJob?.cancel()

        val cal = Calendar.getInstance()


        getAppStatsJob = statDataRepository.getStatDataByDate("${cal.get(Calendar.DATE).toString()} ${monthFromDateInString()}, ${
            cal.get(
                Calendar.YEAR)
        }").onEach { it2 ->
            _points.value =
                1000 - ((it2.dailyUsedAppStatsList.sumOf { it.foregroundTime }) / 60000)

        }.launchIn(viewModelScope)
    }
}