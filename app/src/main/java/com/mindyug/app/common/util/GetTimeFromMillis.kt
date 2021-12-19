package com.mindyug.app.common.util

import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

fun getDurationBreakdown(millis: Long): String? {
    var millis = millis
    require(millis >= 0) { "Duration must be greater than zero!" }
    val days: Long = TimeUnit.MILLISECONDS.toDays(millis)
    millis -= TimeUnit.DAYS.toMillis(days)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis)
    val sb = StringBuilder(64)
    if (hours > 0) {
        sb.append(hours)
        sb.append(" Hr ")
    }
    sb.append(minutes)
    sb.append(" Min ")
    return sb.toString()
}