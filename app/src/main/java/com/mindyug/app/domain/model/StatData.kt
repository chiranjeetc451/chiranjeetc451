package com.mindyug.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class StatData(
    val dailyUsedAppStatsList: List<AppStat>?,
@PrimaryKey
val date: Date? = null
)