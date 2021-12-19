package com.mindyug.app.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.StatData
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface StatDataDao {

    @Query("SELECT * FROM statdata WHERE loggedDate = :loggedDate")
    fun getStatDataByDate(loggedDate: String): Flow<StatData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setStatData(statData: StatData)

    @Query("SELECT * FROM statdata")
    fun getAllEntries(): Flow<List<StatData>>

}