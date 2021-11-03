package com.mindyug.app.data.repository

import com.mindyug.app.data.data_source.StatDataDao
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.*

class StatDataRepositoryImpl(private val dao: StatDataDao) : StatDataRepository {

    override suspend fun getStatDataByDate(date: Date): StatData {
        return dao.getStatDataByDate(date)
    }

    override suspend fun setStatData(statData: StatData) {
        dao.setStatData(statData)
    }

}