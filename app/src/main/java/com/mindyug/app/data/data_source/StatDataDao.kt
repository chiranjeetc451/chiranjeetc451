package com.mindyug.app.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindyug.app.domain.model.StatData
import java.util.*

@Dao
interface StatDataDao {

    @Query("SELECT * FROM statdata WHERE date = :date")
    suspend fun getStatDataByDate(date:Date): StatData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setStatData(statData: StatData)
}