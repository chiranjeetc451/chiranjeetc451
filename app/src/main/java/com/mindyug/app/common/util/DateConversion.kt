package com.mindyug.app.common.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun getMonthFromDate(date: Date): String{
    val pattern = "MM"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}

fun getDateFromDate(date: Date): String{
    val pattern = "dd"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}

fun getYearFromDate(date: Date): String{
    val pattern = "YYYY"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}

fun getPrimaryKeyDate(date: Date): String{
    val pattern = "dd MMM, YYYY"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}

fun monthFromDateInString(): String? {
    val pattern = "MMM"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(Calendar.getInstance().time)
}