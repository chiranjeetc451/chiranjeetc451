package com.mindyug.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StatData(
    val dailyUsedAppStatsList: MutableList<AppStat>,
    val date: String,
    val month: String,
    val year: String,
    @PrimaryKey
    val loggedDate: String
)