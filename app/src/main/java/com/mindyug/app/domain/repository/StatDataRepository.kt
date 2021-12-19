package com.mindyug.app.domain.repository

import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import kotlinx.coroutines.flow.Flow
import java.util.*

interface StatDataRepository {

    fun getStatDataByDate(loggedDate: String): Flow<StatData>

    suspend fun setStatData(statData: StatData)

    fun getAllEntries():Flow<List<StatData>>

}