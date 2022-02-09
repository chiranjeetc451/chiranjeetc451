package com.mindyug.app.background

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.mindyug.app.R
import com.mindyug.app.common.util.monthFromDateInString
import com.mindyug.app.data.preferences.PointSysUtils
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.utils.PointsReceiver
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

@HiltWorker
class PointCollectWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val pointSysUtils: PointSysUtils,
    private val statDataRepository: StatDataRepository,
    private val workManager: WorkManager

) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {

//
//            val cal = java.util.Calendar.getInstance()
//            cal[Calendar.HOUR_OF_DAY] = 0
//            cal[Calendar.MINUTE] = 0
//            cal[Calendar.SECOND] = 0
//            val startTime = cal.timeInMillis
//
//            val endTime = System.currentTimeMillis()
//
//            val job: Job = Job()
//            val coroutineScope = CoroutineScope(job + Dispatchers.IO)
//
//
//
//                pointSysUtils.addPoints(getPoints(applicationContext))
//
//
//                statDataRepository.setStatData(
//                    StatData(
//                        getPurifiedList(startTime, endTime, applicationContext),
//                        cal.get(Calendar.DATE).toString(),
//                        cal.get(Calendar.MONTH).toString(),
//                        cal.get(Calendar.YEAR).toString(),
//                        "${cal.get(Calendar.DATE).toString()} ${monthFromDateInString()}, ${
//                            cal.get(
//                                Calendar.YEAR
//                            ).toString()
//                        }"
//                    )
//                )
//
//
//
//
//





//            makeStatusNotification("Collect points to win exciting prizes!", applicationContext)
//
//
//            val uploadWorkRequest: WorkRequest =
//                OneTimeWorkRequestBuilder<PointResetWorker>()
//                    .setInitialDelay(12, TimeUnit.HOURS)
//                    .addTag("PointResetWorker")
//                    .build()
////
//            workManager.enqueue(uploadWorkRequest)



            Result.success()


        } catch (e: Exception) {
            Log.d("workABC", "error")

            Result.failure()
        }
    }
}

private fun getPoints(context: Context): Long {
    val endTime = System.currentTimeMillis()

    val cal = java.util.Calendar.getInstance()
    cal[Calendar.HOUR_OF_DAY] = 0
    val startTime = cal.timeInMillis

    return getPointsByStartAndEndTime(startTime, endTime, context)

}


fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Collect points!"
        val description = "Collect your daily MindYug Points."
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("VERBOSE_NOTIFICATION", name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, "VERBOSE_NOTIFICATION")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Collect points!")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(1, builder.build())
}

private fun getPointsByStartAndEndTime(startTime: Long, endTime: Long, context: Context): Long {

    val list = getStatsList(startTime, endTime, context)    // return a list of events
    val list2 = appList(context)        // return a list of installed apps
    val googlePackages = mutableListOf(
        "com.android.chrome",
        "com.google.android.youtube",
        "com.google.android.gm",
        "com.google.android.googlequicksearchbox",
        "com.google.android.apps.photos",
//        "com.google.android.apps.maps",
        "com.google.android.apps.tachyon",
        "com.google.android.apps.youtube.music",
        "com.google.android.apps.docs",
        "com.google.android.apps.googleassistant",
        "com.google.android.apps.nbu.files",
        "com.google.android.apps.messaging",
        "com.google.android.calendar",
//        "com.google.android.keep",
    ) //google system packages
    for (name in googlePackages) {
        val ai: ApplicationInfo = try {
            context.packageManager.getApplicationInfo(
                name,
                PackageManager.GET_META_DATA
            )
        } catch (e: Exception) {
            continue
        }
        list2.add(ai)
    }
    val purifiedList: MutableList<AppStat> = mutableListOf()
    for (name in list2) {
//            Log.d("tag", "package: ${app.name}, time: ${app.time} now111")
//            Log.d("tag","package: ${app.packageName}")
        var n: Long = 0
        for (app in list) {
            if (app.packageName == name.packageName) {
                n += app.foregroundTime
            }
        }
        purifiedList.add(AppStat(name.packageName, n))
    }
    for (app in purifiedList) {
        if (app.foregroundTime.equals(0)) {
            purifiedList.remove(app)
        }
    }
    val totalTime = purifiedList.sumOf { it.foregroundTime }
    return 1000 - totalTime / 60000
}


fun getPurifiedList(
    startTime: Long,
    endTime: Long,
    context: Context
): MutableList<AppStat> {

    val list = getStatsList(startTime, endTime, context)           // return a list of events
    val list2 = appList(context)                          // return a list of installed apps

    val googlePackages = mutableListOf(
        "com.android.chrome",
        "com.google.android.youtube",
        "com.google.android.gm",
        "com.google.android.apps.maps",
        "com.google.android.googlequicksearchbox",
        "com.google.android.apps.photos",
        "com.google.android.apps.maps",
        "com.google.android.apps.tachyon",
        "com.google.android.apps.youtube.music",
        "com.google.android.apps.docs",
        "com.google.android.apps.googleassistant",
        "com.google.android.apps.nbu.files",
        "com.google.android.apps.messaging",
        "com.google.android.calendar",
        "com.google.android.keep",
    )
    for (name in googlePackages) {
        val ai: ApplicationInfo = try {
            context.packageManager.getApplicationInfo(
                name,
                PackageManager.GET_META_DATA
            )
        } catch (e: Exception) {
            continue
        }
        list2.add(ai)
    }
    val purifiedList: MutableList<AppStat> = mutableListOf()
    for (name in list2) {
        var n: Long = 0
        for (app in list) {
            if (app.packageName == name.packageName) {
                n += app.foregroundTime
            }
        }
        purifiedList.add(AppStat(name.packageName, n))
    }
    for (app in purifiedList) {
        if (app.foregroundTime.equals(0)) {
            purifiedList.remove(app)
        }
    }
    return purifiedList
}


@Throws(PackageManager.NameNotFoundException::class)
fun getStatsList(startTime: Long, endTime: Long, context: Context): MutableList<AppStat> {
    val usm: UsageStatsManager =
        (context.getSystemService(ComponentActivity.USAGE_STATS_SERVICE) as UsageStatsManager)
//        val appList = ArrayList<App>()
    val appList: MutableList<AppStat> = mutableListOf()

//        val allEvents = ArrayList<UsageEvents.Event>()
    val allEvents: MutableList<UsageEvents.Event> = mutableListOf()
    val usageEvents: UsageEvents = usm.queryEvents(startTime, endTime)
    var event: UsageEvents.Event
    var app: AppStat?
    while (usageEvents.hasNextEvent()) {
        event = UsageEvents.Event()
        usageEvents.getNextEvent(event)
        if (event.eventType == 1 || event.eventType == 2) {
            allEvents.add(event)
        }
    }

//        Collections.sort(allEvents, sortEv())
    for (i in 1 until allEvents.size) {
        val event0 = allEvents[i]
        val event1 = allEvents[i - 1]
        if (event1.eventType == 1 && event0.eventType == 2 && event1.packageName == event0.packageName) {
//                icon = packageManager.getApplicationIcon(E1.packageName)
            val diff = event0.timeStamp - event1.timeStamp
            app = AppStat(
//                    icon,
                event1.packageName,
                diff
            )
            appList.add(app)

        }
    }
    return appList
}

@SuppressLint("QueryPermissionsNeeded")
fun appList(context: Context): MutableList<ApplicationInfo> {
    return context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA).filterNot {
        (it.flags and ApplicationInfo.FLAG_SYSTEM) != 0
    } as MutableList<ApplicationInfo>
}
