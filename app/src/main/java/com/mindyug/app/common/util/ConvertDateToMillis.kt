package com.mindyug.app.common.util

import android.icu.util.Calendar

fun convertDateToMillis(date: Int): Long {
    val d = Calendar.getInstance()
    d[Calendar.DATE] = date
    return d.timeInMillis
}
