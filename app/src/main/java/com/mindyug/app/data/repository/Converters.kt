package com.mindyug.app.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mindyug.app.domain.model.AppStat
import java.util.*

class Converters {

    @TypeConverter
    fun listToJson(value: List<AppStat>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<AppStat>::class.java).toList()

}