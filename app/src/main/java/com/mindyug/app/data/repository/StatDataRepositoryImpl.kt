package com.mindyug.app.data.repository

import com.mindyug.app.data.data_source.StatDataDao
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class StatDataRepositoryImpl(private val dao: StatDataDao) : StatDataRepository {

    override fun getStatDataByDate(loggedDate: String): Flow<StatData> {
        return dao.getStatDataByDate(loggedDate)
    }

    override suspend fun setStatData(statData: StatData) {
        dao.setStatData(statData)
    }

}