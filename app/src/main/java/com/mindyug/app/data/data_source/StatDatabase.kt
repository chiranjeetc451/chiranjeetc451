package com.mindyug.app.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mindyug.app.data.repository.Converters
import com.mindyug.app.domain.model.StatData

@Database(
    entities = [StatData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class StatDatabase : RoomDatabase() {
    abstract val statDao: StatDataDao
    companion object{
        const val DATABASE_NAME = "stats_db"
    }
}