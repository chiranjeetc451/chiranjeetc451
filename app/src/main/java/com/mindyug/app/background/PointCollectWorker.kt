package com.mindyug.app.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mindyug.app.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PointCollectWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {

            val sharedPref =
                applicationContext.getSharedPreferences("pointSysUtils", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("collectButtonState", true).apply()

            makeStatusNotification("Collect points to win exciting prizes!", applicationContext)

            Result.success()
        } catch (e: Exception) {
            Log.d("workABC", "error")

            Result.failure()
        }
    }
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
