package com.mindyug.app.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject
constructor(
    private val statDataRepository: StatDataRepository
) : ViewModel() {

    fun loadStatData(list: MutableList<AppStat>, date: Date) {
        viewModelScope.launch {
            statDataRepository.setStatData(StatData(list, date))
        }
    }
}