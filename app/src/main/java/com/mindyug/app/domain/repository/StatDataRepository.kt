package com.mindyug.app.domain.repository

import com.mindyug.app.domain.model.StatData
import kotlinx.coroutines.flow.Flow
import java.util.*

interface StatDataRepository {

    suspend fun getStatDataByDate(date: Date): StatData
    
    suspend fun setStatData(statData: StatData)

}